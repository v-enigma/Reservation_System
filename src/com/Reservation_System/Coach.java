package com.Reservation_System;

import java.util.List;

public class Coach {
	private final int id;
	private final String name;
	private List<Seat> seats;
	Coach(int id, List<Seat> seats, String name){
		this.id = id;
		this.seats = seats;
		this.name = name;
	}
	public int getId() {
		return this.id;
	}
	public int getSeatsCount(){
		return seats.size();
	}
	
	public String getName() { //updated
		return name;
	}
	
	public Seat getSeat(int id) {
		return seats.get(id);
	}
	
	
	
		
	
}
