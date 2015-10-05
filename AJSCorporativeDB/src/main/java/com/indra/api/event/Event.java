package com.indra.api.event;

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
@Table(name="corpt002_events")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private int iduser;
	
	@Column
	private String title;
	
	@Column
	private String start;
	
	@Column
	private String end;
	
	@Column
	private String description;
	
	@Column
	private boolean allday;
	
	@Column
	private boolean editable;
	
	@Column
	private boolean durationeditable;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIduser() {
		return iduser;
	}
	
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStart() {
		return start;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean getAllday() {
		return allday;
	}
	
	public void setAllday(boolean allday) {
		this.allday = allday;
	}
	
	public boolean getEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public boolean getDurationeditable() {
		return durationeditable;
	}
	
	public void setDurationeditable(boolean durationeditable) {
		this.durationeditable = durationeditable;
	}
}
