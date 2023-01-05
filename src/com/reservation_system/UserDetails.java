package com.reservation_system;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.enums.Gender;
import com.enums.SeatType;


public class UserDetails {
	private String name;
	private LocalDate dateOfBirth;
	private Gender gender;
	private SeatType seatPreference;
	
	UserDetails(String name, LocalDate dateOfBirth, Gender gender, SeatType seatPreference){
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.seatPreference = seatPreference;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAge() {
		LocalDate dt = LocalDate.now();
        long years = ChronoUnit.YEARS.between(dateOfBirth , dt);
        return years;
	}
	
	public void dateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public SeatType getSeatPreference() {
		return seatPreference;
	}
	public  void setSeatPreference(SeatType seatPreference) {
		this.seatPreference = seatPreference;
	}
	

	/*
	 * public static void main(String[] args) { Passenger p = new Passenger("venu",
	 * 24, Gender.M, null); System.out.println(p.seatPreference); }
	 */
	
	
	
	
}
