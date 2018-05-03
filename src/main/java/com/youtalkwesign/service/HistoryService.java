package com.youtalkwesign.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtalkwesign.model.History;
import com.youtalkwesign.model.HistoryKey;
import com.youtalkwesign.model.Video;
import com.youtalkwesign.repository.HistoryRepository;

@Service
public class HistoryService {

	@Autowired
	HistoryRepository repo;

	public void addVideoToHistory(String username, String videoId) {
		repo.save(new History(new HistoryKey(username, videoId)));
	}

	public List<Video> getHistoryVideos(String username) {
		// initialize result
		List<Video> result = new ArrayList<Video>();

		Iterable<History> histories = repo.findAll();
		for (History history : histories) {
			if (history.getHistoryKey().getUsername().equals(username)) {
				Video video = new Video();
				video.setId(history.getHistoryKey().getVideoId());
				video.setTitle(getTitleQuietly(history.getHistoryKey().getVideoId()));
				video.setThumbnailImageUrl("https://i.ytimg.com/vi/" + 
						history.getHistoryKey().getVideoId() + "/maxresdefault.jpg");
				result.add(video);
			}		
		}
		return result;
	}

	public String getTitleQuietly(String videoId) {
		try {
			if (videoId != null) {
				URL embededURL = new URL("http://www.youtube.com/oembed?url=" + "http://www.youtube.com/watch?v="
						+ videoId + "&format=json");

				return new JSONObject(IOUtils.toString(embededURL, "UTF-8")).getString("title");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
