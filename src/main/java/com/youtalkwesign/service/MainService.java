package com.youtalkwesign.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
				if (text.charAt(0) == ' ') {
					text = text.substring(1);
				}
				
				Text temp = new Text();
				temp.setStart(currentTexts.get(i).getStart());
				temp.setDur(currentTexts.get(i).getDur());
				temp.setText(text);
				newTexts.add(temp);		
			}
		}			
		return newTexts;
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
			
			// add all letters also (count: 26)
	        for(char c = 'A'; c <= 'Z'; c++) {
	        	wordSet.add("" + c);
	        }
	           
		}
		
		// get word set from the database and
		// convert iterable to array list
		Iterable<Word> source = wordRepo.findAll(wordSet);
		List<Word> target = new ArrayList<Word>();
		source.forEach(target::add);
		
		return target;		
	}	
	
	/*
	YouTalkWeSign will be a web application which is designed to serve people with hearing impairments or deafness. 
	Through an on-screen avatar, the app will translate the spoken words in YouTube videos into sign language. 
	The app can be run by replacing the "youtube" word in the video address with "youtalkwesign".
	YouTalkWeSign will be a web application which is designed to serve people with hearing impairments or deafness. 
	Through an on-screen avatar, the app will translate the spoken words in YouTube videos into sign language. 
	The app can be run by replacing the "youtube" word in the video address with "youtalkwesign".
	YouTalkWeSign will be a web application which is designed to serve people with hearing impairments or deafness. 
	Through an on-screen avatar, the app will translate the spoken words in YouTube videos into sign language. 
	The app can be run by replacing the "youtube" word in the video address with "youtalkwesign".
	*/
}
