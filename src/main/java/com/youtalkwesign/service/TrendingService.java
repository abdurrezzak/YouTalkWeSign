package com.youtalkwesign.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtalkwesign.model.Heart;
import com.youtalkwesign.model.History;
import com.youtalkwesign.model.Video;
import com.youtalkwesign.repository.HeartRepository;
import com.youtalkwesign.repository.HistoryRepository;

@Service
public class TrendingService {
	
	@Autowired
	HistoryRepository historyRepo;
	
	@Autowired
	HeartRepository heartRepo;

	public List<Video> getTrendingVideos() {
		
		// each history video has one point
		Iterable<History> historyVideos = historyRepo.findAll();
		
		// each hearted video has one point
		Iterable<Heart> heartVideos = heartRepo.findAll();
		
		// initialize points
		HashMap<String, Integer> points = new HashMap<String, Integer>();
		
		for (History history : historyVideos) {
			String key = history.getHistoryKey().getVideoId();
			Integer value = points.get(key);
			if (value != null) {
				points.put(key, points.get(key) + 1);
			} else {
			    points.put(key, 1); // start by one point
			}
		}
		
		for (Heart heart : heartVideos) {
			String key = heart.getVideoId();
			Integer value = points.get(key);
			if (value != null) {
				points.put(key, points.get(key) + 1);
			} else {
			    points.put(key, 1); // start by one point
			}
		}
		
		// sort by points
		Map<String, Integer> sortedPoints = sortByValue(points);
		
		// initialize videos
		List<Video> trendingVideos = new ArrayList<Video>();
		
		// iterate map (first 10 items from reverse)
		List<String> keyList = new ArrayList<String>(sortedPoints.keySet());
		for (int i = keyList.size() - 1; i > keyList.size() - 6; i--) {
		    String key = keyList.get(i);
		    
		    // init video
		    Video video = new Video();
			video.setId(key);
			video.setTitle(getTitleQuietly(key));
			video.setThumbnailImageUrl("https://i.ytimg.com/vi/" + key + "/maxresdefault.jpg");
			trendingVideos.add(video);		    
		}
		
		// return result
		return trendingVideos;
	}
	
	// hash map sorting algorithm
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
	
	private static String getTitleQuietly(String videoId) {
	    try {
	        if (videoId != null) {
	            URL embededURL = new URL("http://www.youtube.com/oembed?url=" +
	                "http://www.youtube.com/watch?v=" + videoId + "&format=json"
	            );

	            return new JSONObject(IOUtils.toString(embededURL, "UTF-8")).getString("title");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
