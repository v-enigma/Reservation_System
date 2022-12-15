package com.Reservation_System;


import com.Reservation_System.Enum.SeatStatus;
import com.Reservation_System.Enum.Type;

public class Seat {
	private final int id;
	private final Type type;
	
	SeatStatus status;
	Seat(int id, Type type, SeatStatus status){
		this.id = id;
		this.type = type;
		this.status = status;
	}
	public Type getType() {
		return type;
	}
	
	public SeatStatus getStatus() {
		return status;
	}
	public void setStatus(SeatStatus status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	

}
