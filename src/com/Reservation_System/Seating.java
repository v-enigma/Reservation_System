package com.Reservation_System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Seating {
	private final List<Coach> coaches= new ArrayList<>();
	private final HashMap<String, ArrayList<Integer >> bookedSeats ;
	private int availableSeats;
	
	Seating(HashMap<String, ArrayList<Integer>> bookedSeats){
		this.bookedSeats = bookedSeats;
		this.availableSeats = 0;	
	}
	public void addCoach(Coach coach) {
		coaches.add(coach);
		this.availableSeats+=coach.getSeatsCount(); 
		
	}
	public void incrementAvailableSeats() {
		if(coaches.size()> 0 && this.availableSeats < coaches.size()* coaches.get(0).getSeatsCount())
			this.availableSeats++;
	}
	public void decrementAvailableSeats() {
		if(coaches.size()>0 && this.availableSeats > 0 ) {
			this.availableSeats--;
		}
	}
	
	public int getAvailableSeats() {
		return this.availableSeats;
	}
	public HashMap<String, ArrayList<Integer>> getBookedSeats(){
		return bookedSeats;
		
	}
	
}
