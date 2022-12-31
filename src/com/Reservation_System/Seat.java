package com.Reservation_System;


import com.Reservation_System.Enum.SeatType;


public class Seat {
	private final int id;
	private final SeatType type;
	Seat(int id, SeatType type){
		this.id = id;
		this.type = type;
		
	}
	public SeatType getType() {
		return type;
	}
	
	
	public int getId() {
		return id;
	}
	
}
