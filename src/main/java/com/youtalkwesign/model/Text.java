package com.youtalkwesign.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.NONE)
public class Text {
	private String start;
	private String dur;
	private String text;

	@XmlAttribute
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	@XmlAttribute
	public void setDur(String dur) {
		this.dur = dur;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@XmlValue
	public String getText() {
		return text;
	}
	public String getDur() {
		return dur;
	}
	
	@Override
	public String toString() {
		return "Text [start=" + start + ", duration=" + dur + ", text=" + text + "]";
	}
}
