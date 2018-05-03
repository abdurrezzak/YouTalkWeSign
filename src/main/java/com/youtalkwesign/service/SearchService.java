package com.youtalkwesign.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.youtalkwesign.model.Video;

@Service
public class SearchService {
	
	private static final long RESULTS_WITH_CAPTIONS = 6;
	private static final long RESULTS_WITHOUT_CAPTIONS = 4;
	private static final String apiKey = "AIzaSyBfIRMYnGtvQW5BYtecoTBdmlMjdfazeAw";
	private final YouTube youtube = getYouTube();
	
	public List<Video> getResultsWithCaptions(String keyword) {
		List<Video> videos = new ArrayList<Video>();

		try {
			// define what info we want to get
			YouTube.Search.List search = youtube.search().list("id,snippet");

			// set our credentials
			search.setKey(apiKey);

			// set the search term
			search.setQ(keyword);
			
			// choose videos with captions
			search.setVideoCaption("closedCaption");
			
			// we only want video results
			search.setType("video");

			// set the fields that we're going to use
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");

			// set the max results
			search.setMaxResults(RESULTS_WITH_CAPTIONS);

			// perform the search and parse the results
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			if (searchResultList != null) {
				for (SearchResult result : searchResultList) {
					Video video = new Video();
					video.setId(result.getId().getVideoId());
					video.setTitle(result.getSnippet().getTitle());		
					video.setThumbnailImageUrl(result.getSnippet().getThumbnails().getDefault().getUrl());
					video.setSubtitles(true);			
					videos.add(video);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return videos;
	}

	public List<Video> getResultsWithoutCaptions(String keyword) {
		List<Video> videos = new ArrayList<Video>();

		try {
			// define what info we want to get
			YouTube.Search.List search = youtube.search().list("id,snippet");

			// set api key
			search.setKey(apiKey);

			// set the search term
			search.setQ(keyword);
			
			// choose videos with captions
			search.setVideoCaption("none");
			
			// we only want video results
			search.setType("video");

			// set the fields that we're going to use
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");

			// set the max results
			search.setMaxResults(RESULTS_WITHOUT_CAPTIONS);

			// perform the search and parse the results
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			if (searchResultList != null) {
				for (SearchResult result : searchResultList) {
					Video video = new Video();
					video.setId(result.getId().getVideoId());
					video.setTitle(result.getSnippet().getTitle());		
					video.setThumbnailImageUrl(result.getSnippet().getThumbnails().getDefault().getUrl());
					video.setSubtitles(false);			
					videos.add(video);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return videos;
	}

	private static YouTube getYouTube() {
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), 
                (reqeust) -> {}).setApplicationName("youtube-search").build();
    	 
        return youtube;
    }
}
