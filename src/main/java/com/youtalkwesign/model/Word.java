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
	
	@Column(name = "length")
	private double length;

	public Word() {
		word = "";
		videoUrl = "";
		length = 0.0;
	}

	public Word(String word, String videoUrl, double length) {
		this.word = word;
		this.videoUrl = videoUrl;
		this.length = length;
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
	
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "Word [word=" + word + ", videoUrl=" + videoUrl + ", length=" + length + "]";
	}
}
