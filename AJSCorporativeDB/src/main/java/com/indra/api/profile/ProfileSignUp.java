package com.indra.api.profile;

import java.sql.Timestamp;

public class ProfileSignUp {
	
	private int iduser;
	
	private String city;
	
	private String country;
	
	private Timestamp birthday;
	
	public int getIduser() {
		return iduser;
	}
	
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Timestamp getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
}
