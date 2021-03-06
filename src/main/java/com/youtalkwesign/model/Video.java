package com.youtalkwesign.model;

public class Video {
	private String id;
	private String title;
	private String thumbnailImageUrl;
	private boolean subtitles;
	
	public Video() {
		super();
		subtitles = true;
	}
	public Video(String id, String title, String thumbnailImageUrl) {
		super();
		this.id = id;
		this.title = title;
		this.thumbnailImageUrl = thumbnailImageUrl;
		subtitles = true;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}
	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}
	public boolean isSubtitles() {
		return subtitles;
	}
	public void setSubtitles(boolean subtitles) {
		this.subtitles = subtitles;
	}
	
	
}
