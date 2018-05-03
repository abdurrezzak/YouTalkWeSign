package com.youtalkwesign.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.language.v1.AnalyzeSyntaxRequest;
import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;
import com.google.cloud.language.v1.Token;
import com.youtalkwesign.model.Text;
import com.youtalkwesign.model.Transcript;
import com.youtalkwesign.model.Word;
import com.youtalkwesign.repository.WordRepository;

@Service
public class MainService {
	
	@Autowired
	WordRepository wordRepo;
	
	public List<Text> getTranscript(String v) {
		JAXBContext jc = null;
		Transcript root = null;
		try {
			String uri = "http://video.google.com/timedtext?lang=en&v=" + v;
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");
			InputStream xml = connection.getInputStream();
			
			jc = JAXBContext.newInstance(Transcript.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();    
	        root = (Transcript) unmarshaller.unmarshal(xml);
		} catch (JAXBException e) {
			
		} catch (MalformedURLException e) {
			
		} catch (ProtocolException e) {
			
		} catch (IOException e) {
			
		}
		
		// initialize texts
		List<Text> newTexts = new ArrayList<Text>();
		
		if (root != null) {
			// get transcript	
			List<Text> currentTexts = root.getTexts();			
			for (int i = 0; i < currentTexts.size(); i++) {
				String text = currentTexts.get(i).getText();
				text = text.replace("&#39;", "'"); // it is ' character
				text = text.replaceAll("[\\p{Punct}&&[^'-]]+", " "); // remove punctuations except ' and -
				text = text.toLowerCase();
				text = text.replace("ı", "i");
				text = text.replaceAll("[\\t\\n\\r]+"," "); // replace new lines with spaces
				text = text.trim().replaceAll(" +", " "); // replace more than one spaces with one
				text = text.replace("'m", " am");
				text = text.replace("'s", " is");
				text = text.replace("'re", " are");			
				text = text.replace("'ve", " have");
				text = text.replace("'ll", " will");
				text = text.replace("'d", " would");
				text = text.replace("can't", "cannot");
				text = text.replace("n't", "  not");
				text = text.replace("let's", "let us");
				text = text.replace("'", "");
				text = text.replace("quot", "");
				text = text.trim().replaceAll(" +", " "); // replace more than one spaces with one
				if (text.length() > 0) {
					if (text.charAt(0) == ' ') {
						text = text.substring(1);
					}
				}
				
				
				Text temp = new Text();
				
				// set start
				String start = currentTexts.get(i).getStart();
				temp.setStart(currentTexts.get(i).getStart());
				
				// set duration
				if (i != currentTexts.size() - 1) {
					String nextStart = currentTexts.get(i + 1).getStart();
					double startDouble = Double.parseDouble(start);		
					double nextStartDouble = Double.parseDouble(nextStart);
					double newDurDouble = nextStartDouble - startDouble;
					temp.setDur("" + newDurDouble);
				} else {
					temp.setDur(currentTexts.get(i).getDur());
				}		
				
				// set text
				temp.setText(text);
				
				newTexts.add(temp);		
			}
		}			
		// get simple forms
		List<Text> simpleTexts = getSimpleForms(newTexts);
		
		// return 
		return simpleTexts;
	}
	
	public List<Word> getWords(List<Text> texts) {
		// initialize helper set
		Set<String> wordSet = new HashSet<String>();
		for (int i = 0; i < texts.size(); i++) {
			// get text and delete commas and dots
			String text = texts.get(i).getText();
			
			// split text into words
			String[] words = text.split("\\s+");
			for (int j = 0; j < words.length; j++) {
			    // You may want to check for a non-word character before blindly
			    // performing a replacement
			    // It may also be necessary to adjust the character class
			    words[j] = words[j].replaceAll("[^\\w]", "");
			}
			
			// add each word to list with the corresponding video url
			for (int k = 0; k < words.length; k++) {				
				String word = words[k];							
				wordSet.add(word);
			}           
		}
			
		// get word set from the database and
		// convert iterable to array list
		Iterable<Word> source = wordRepo.findAll(wordSet);
		List<Word> target = new ArrayList<Word>();		
		source.forEach(word -> {
			word.setVideoUrl("sign-videos/" + word.getWord() + ".mp4");
			target.add(word);
		});
		
		return target;		
	}	
	
	private List<Text> getSimpleForms(List<Text> texts) {
		
		// initialize result
		List<Text> result = new ArrayList<Text>();
		
		// initialize list
		String lines = "";
		for (Text text : texts) {
			lines = lines + text.getText() + " LINEBREAK ";
		}
		
		List<String> simpleLines = new ArrayList<String>();
		
		GoogleCredentials credentials;
		LanguageServiceSettings settings;
	    File credentialsPath = new File("D:/service-account-file.json");  
	    // TODO: D:/service-account-file.json OR /home/service-account-file.json
	    try (FileInputStream serviceAccountStream = new FileInputStream(credentialsPath)) {
	    	credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
	      	FixedCredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);	    
			settings = LanguageServiceSettings.newBuilder()
											.setCredentialsProvider(credentialsProvider)
											.build();
			LanguageServiceClient language = LanguageServiceClient.create(settings);
			Document doc = Document.newBuilder()
					.setContent(lines)
					.setType(Type.PLAIN_TEXT)
					.build();
			AnalyzeSyntaxRequest request = AnalyzeSyntaxRequest.newBuilder()
					.setDocument(doc)
					.setEncodingType(EncodingType.UTF16)
					.build();
			
			// analyze the syntax in the given text
			AnalyzeSyntaxResponse response = language.analyzeSyntax(request);	
			
			// response			
			for (Token token : response.getTokensList()) {
				simpleLines.add(token.getLemma());
			}		
	    } catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		String fullTranscript = "";		
		for (String line : simpleLines) {
			fullTranscript = fullTranscript + line + " ";
		}
		
		// split them according to LINEBREAK sentinel value
		List<String> updatedLines = 
				new ArrayList<String>(Arrays.asList(fullTranscript.split("LINEBREAK")));
		
		// update texts and add it to result
		for (int i = 0; i < texts.size(); i++) {
			Text text = texts.get(i);
			String updated = updatedLines.get(i);
			updated = updated.replace("--", ""); // remove dashes
			updated = updated.replaceFirst("^\\s*", ""); // remove front spaces from lines
			updated = updated.replaceAll("\\s+$", ""); // remove end spaces from lines
			text.setText(updated);
			
			result.add(text);
		}		
		return result;
	}
}

