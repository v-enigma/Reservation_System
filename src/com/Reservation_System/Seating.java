package com.Reservation_System;

import java.util.ArrayList;
import java.util.List;

public abstract class Seating {
	private final List<Coach> coaches= new ArrayList<>();
	
	private int availableSeats;
	
	Seating(){
		
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
	
	
}
