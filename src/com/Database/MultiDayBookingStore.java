package com.Database;

import java.time.LocalDate;
import java.util.HashMap;

public class MultiDayBookingStore { // 120 Days Ahead occupied seats and unoccupied seats  Including Today storage 
	public MultiDayBookingStore(){
		
	}
	private final  HashMap<LocalDate ,BookedAndAvailableSeatsByDateStore >multiDaySleeperBookings = new HashMap<>();
	private final HashMap<LocalDate, BookedAndAvailableSeatsByDateStore> multiDayACBookings= new HashMap<>();
	
	public BookedAndAvailableSeatsByDateStore getSleeperSeating(LocalDate date) {
		return multiDaySleeperBookings.get(date);
	}
	public BookedAndAvailableSeatsByDateStore getACSeating(LocalDate date) {
		return multiDayACBookings.get(date);
	}
	
	public void addSeatAllocationHelper(LocalDate localDate, BookedAndAvailableSeatsByDateStore seatallocationHelper   ) {
		
	}
}
