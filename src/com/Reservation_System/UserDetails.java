package com.Reservation_System;

import com.Reservation_System.Enum.Gender;
import com.Reservation_System.Enum.Type;

public class UserDetails {
	private String name;
	private int age;
	private Gender gender;
	private Type seatPreference;
	
	UserDetails(String name, int age, Gender gender, Type seatPreference){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.seatPreference = seatPreference;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Type getSeatPreference() {
		return seatPreference;
	}
	public  void setSeatPreference(Type seatPreference) {
		this.seatPreference = seatPreference;
	}
	

	/*
	 * public static void main(String[] args) { Passenger p = new Passenger("venu",
	 * 24, Gender.M, null); System.out.println(p.seatPreference); }
	 */
	
	
	
	
}
