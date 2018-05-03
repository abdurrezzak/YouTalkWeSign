package com.youtalkwesign.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class History {
	@EmbeddedId
	private HistoryKey historyKey;

	public History() {
		super();
	}

	public History(HistoryKey historyKey) {
		super();
		this.historyKey = historyKey;
	}

	public HistoryKey getHistoryKey() {
		return historyKey;
	}

	public void setHistoryKey(HistoryKey historyKey) {
		this.historyKey = historyKey;
	}
	
}
