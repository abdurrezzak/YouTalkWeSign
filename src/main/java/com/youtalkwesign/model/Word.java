package com.youtalkwesign.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Word {
	@Id
	private String word;

	@Column(name = "video_url")
	private String videoUrl;

	public Word() {
		word = "";
		videoUrl = "";
	}

	public Word(String word, String videoUrl) {
		this.word = word;
		this.videoUrl = videoUrl;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	@Override
	public String toString() {
		return "Word [word=" + word + ", videoUrl=" + videoUrl + "]";
	}
}
