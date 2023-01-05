package com.reservation_system;

import com.enums.SeatType;

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
