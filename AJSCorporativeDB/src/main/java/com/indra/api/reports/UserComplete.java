package com.indra.api.reports;

import java.sql.Timestamp;

/**
 * 
 * @author arommartinez
 *
 */

public class UserComplete {
	
	private int id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String password;
	
	private int image;
	
	private String biography;
	
	private String city;
	
	private String country;

	private Timestamp birthday;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public int getImage() {
		return image;
	}
	
	public void setImage(int image) {
		this.image = image;
	}
	
	public String getBiography() {
		return biography;
	}
	
	public void setBiography(String biography) {
		this.biography = biography;
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
