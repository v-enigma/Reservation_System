package com.Reservation_System;

import java.util.List;
import java.util.ArrayList;
import com.Reservation_System.Enum.SeatStatus;

public class Coach {
	private final int id;
	private List<Seat> seats;
	Coach(int id, List<Seat> seats){
		this.id = id;
		this.seats = seats;
	}
	public int getId() {
		return this.id;
	}
	public int getSeatsCount(){
		return seats.size();
	}
	public List<Integer> getAvailableSeats(){
		List<Integer> AvailableSeatIDList = new ArrayList<>();
		for(Seat seat: seats) {
			if(seat.status == SeatStatus.AVL ) {
				AvailableSeatIDList.add(seat.getId());
			}
		}
		return AvailableSeatIDList;
	}
	
	
	
		
	
}
