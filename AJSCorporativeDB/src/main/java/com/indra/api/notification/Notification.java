package com.indra.api.notification;

import java.sql.Timestamp;

public class Notification {
	
	private int iduserto;
	
	private int iduserfrom;
	
	private Type type;
	
	private Timestamp time;
	
	public enum Type {
		MESSAGE,
		REQUEST,
		EVENT,
		CHAT
	}
	
	public int getIduserto() {
		return iduserto;
	}
	
	public void setIduserto(int iduserto) {
		this.iduserto = iduserto;
	}
	
	public int getIduserfrom() {
		return iduserfrom;
	}
	
	public void setIduserfrom(int iduserfrom) {
		this.iduserfrom = iduserfrom;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
