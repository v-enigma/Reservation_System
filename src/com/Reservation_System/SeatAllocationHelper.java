package com.Reservation_System;

import java.util.ArrayList;
import java.util.HashMap;

public class SeatAllocationHelper {
	private int coachCount;
	private int seatsInCoach;
	private final HashMap<String, ArrayList<String >> bookedSeats = new HashMap<>();
	private int availableSeats;
	
	SeatAllocationHelper( int coachCount, int seatsInCoach){
		this.coachCount = coachCount;
		this.seatsInCoach = seatsInCoach;
		this.availableSeats = coachCount * seatsInCoach;	
	}
	public void increaseCoach(int count) {
		coachCount+=count;
		this.availableSeats+=(coachCount*seatsInCoach );
		
	}

	/*
	 * public void incrementAvailableSeats() { if(coaches.size()> 0 &&
	 * this.availableSeats < coaches.size()* coaches.get(0).getSeatsCount())
	 * this.availableSeats++; } public void decrementAvailableSeats() {
	 * if(coaches.size()>0 && this.availableSeats > 0 ) { this.availableSeats--; } }
	 */
	public int getAvailableSeats() {
		return this.availableSeats;
	}
	public HashMap<String, ArrayList<String>> getBookedSeats(){
		return bookedSeats;
		
	}
	public String searchSeat() {
		String seatId ="";
		return seatId;
	}

}
