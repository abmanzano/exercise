package com.example.exercise.model;

import java.util.Date;

public class User {
	private String id;
	private String firstname; 
	private String lastname; 
	private String email; 
	private Date dateOfBirth; 
	private String mobile;
	private String town;
	
	public User() {
		
	}
	
	public User(final String id, final String firstname, final String lastname, final String email, final Date dateOfBirth,
			final String mobile, final String town) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.dateOfBirth = dateOfBirth; 
		this.mobile = mobile;
		this.town = town;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Id:").append(this.getId()).append(";");
		sb.append("Firstname: ").append(this.getFirstname()).append(";");
		sb.append("Lastname: ").append(this.getLastname()).append(";");
		sb.append("Email: ").append(this.getEmail()).append(";");
		return sb.toString();
	}
}
