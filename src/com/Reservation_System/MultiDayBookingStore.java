package com.Reservation_System;

import java.time.LocalDate;
import java.util.HashMap;

public class MultiDayBookingStore { // multiple Days Ahead Booking Including Today storage => DABIT
	public MultiDayBookingStore(){
		
	}
	private final  HashMap<LocalDate ,SeatAllocationHelper >multiDaySleeperBookings = new HashMap<>();
	private final HashMap<LocalDate, SeatAllocationHelper> multiDayACBookings= new HashMap<>();
	
	public SeatAllocationHelper getSleeperSeating(LocalDate date) {
		return multiDaySleeperBookings.get(date);
	}
	public SeatAllocationHelper getACSeating(LocalDate date) {
		return multiDayACBookings.get(date);
	}
	
	public void addSeatAllocationHelper(LocalDate localDate, SeatAllocationHelper seatallocationHelper  ) {
		
	}
}
