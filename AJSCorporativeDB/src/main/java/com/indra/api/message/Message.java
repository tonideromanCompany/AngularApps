package com.indra.api.message;

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
@Table(name="corpt004_messages")
public class Message{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private int iduserfrom;
	
	@Column
	private int iduserto;
	
	@Column
	private String body;
	
	@Column
	private Timestamp timesend;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
		return timesend;
	}
	
	public void setTime(Timestamp timesend) {
		this.timesend = timesend;
	}
	

}
