package com.youtalkwesign.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtalkwesign.model.Heart;
import com.youtalkwesign.model.Video;
import com.youtalkwesign.repository.HeartRepository;

@Service
@Transactional
public class HeartedService {
	
	@Autowired
	HeartRepository repo;
	
	public boolean hasHeart(String username, String v) {
		Heart heart = repo.findOneByUsernameAndVideoId(username, v);
		
		return heart != null;
	}

	public List<Video> getHeartedVideos(String username) {
		// initialize result
		List<Video> result = new ArrayList<Video>();
		
		List<Heart> hearts = repo.findByUsername(username);
		for (Heart heart : hearts) {
			Video video = new Video();
			video.setId(heart.getVideoId());
			video.setTitle(getTitleQuietly(heart.getVideoId()));
			video.setThumbnailImageUrl("https://i.ytimg.com/vi/" + heart.getVideoId() + "/maxresdefault.jpg");
			result.add(video);
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

	public void unheart(String username, String videoId) {
		repo.deleteByUsernameAndVideoId(username, videoId);
	}
}
