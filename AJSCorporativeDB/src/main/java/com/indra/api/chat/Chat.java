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
@Table(name="corpt010_chat")
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private State state;
	
	@Column
	private int iduserfrom;
	
	@Column
	private int iduserto;
	
	@Column
	private Timestamp time;
	
	public enum State {
		OPENED,
		CLOSED
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
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
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
