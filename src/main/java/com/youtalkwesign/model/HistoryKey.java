package com.youtalkwesign.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class HistoryKey implements Serializable {
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "video_id", nullable = false)
	private String videoId;

	public HistoryKey() {
		super();
	}

	public HistoryKey(String username, String videoId) {
		super();
		this.username = username;
		this.videoId = videoId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	@Override
	public String toString() {
		return "HistoryKey [username=" + username + ", videoId=" + videoId + "]";
	}

}
