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
	
	private static final long MAX_SEARCH_RESULTS = 20;

	public List<Video> getResults(String keyword) {
		List<Video> videos = new ArrayList<Video>();

		try {
			// instantiate youtube object
			YouTube youtube = getYouTube();

			// define what info we want to get
			YouTube.Search.List search = youtube.search().list("id,snippet");

			// set our credentials
			String apiKey = "AIzaSyBfIRMYnGtvQW5BYtecoTBdmlMjdfazeAw";
			search.setKey(apiKey);

			// set the search term
			search.setQ(keyword);

			// we only want video results
			search.setType("video");

			// set the fields that we're going to use
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");

			// set the max results
			search.setMaxResults(MAX_SEARCH_RESULTS);

			// perform the search and parse the results
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			if (searchResultList != null) {
				for (SearchResult result : searchResultList) {
					Video video = new Video();
					video.setId(result.getId().getVideoId());
					video.setTitle(result.getSnippet().getTitle());		
					video.setThumbnailImageUrl(result.getSnippet().getThumbnails().getDefault().getUrl());
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
