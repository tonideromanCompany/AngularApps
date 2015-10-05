package com.indra.api.chat;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author arommartinez
 *
 */

@Entity
@Table(name = "corpt011_chatmessage")
public class ChatMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private int idchat;
	
	@Column
	private int iduserfrom;
	
	@Column
	private int iduserto;
	
	@Column
	private String body;
	
	@Column
	private Timestamp time;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdchat() {
		return idchat;
	}
	
	public void setIdchat(int idchat) {
		this.idchat = idchat;
	}
	
	public int getIduserfrom() {
		return iduserfrom;
	}
	
	public void setIduserfrom(int iduserfrom) {
		this.iduserfrom = iduserfrom;
	}
	
	public int getIduserto() {
		return iduserto;
	}
	
	public void setIduserto(int iduserto) {
		this.iduserto = iduserto;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
