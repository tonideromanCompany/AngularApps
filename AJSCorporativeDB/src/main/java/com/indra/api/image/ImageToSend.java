package com.indra.api.image;

import java.sql.Timestamp;

/**
 * 
 * @author arommartinez
 *
 */

public class ImageToSend {
	
	private int id;
	
	private String url;
	
	private Timestamp time;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setString(String url) {
		this.url = url;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
