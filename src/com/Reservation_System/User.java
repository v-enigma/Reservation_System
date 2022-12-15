package com.Reservation_System;

public class User {
	private final String id;
	private String phoneNumber;
	private  UserDetails userDetails;
	private String mailId;
	User (String id, String phoneNumber, String mailId,UserDetails userDetails){
		this.userDetails = userDetails;
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.mailId = mailId;
		
	}
	public String getId() {
		return id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getMailId() {
		return mailId;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	
}
