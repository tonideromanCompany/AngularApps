package com.indra.api.image;

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
@Table(name = "corpt008_image")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private int iduser;
	
	@Column
	private byte[] image;
	
	@Column
	private Timestamp time;
	
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
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image){
		this.image = image;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
