package com.Database;

import java.time.LocalDate;
import java.util.HashMap;

public class MultiDayBookingStore { // 120 Days Ahead occupied seats and unoccupied seats  Including Today storage 
	public MultiDayBookingStore(){
		
	}
	private final  HashMap<LocalDate ,BookedAndAvailableSeatsByDate >multiDaySleeperBookings = new HashMap<>();
	private final HashMap<LocalDate, BookedAndAvailableSeatsByDate> multiDayACBookings= new HashMap<>();
	
	public BookedAndAvailableSeatsByDate getSleeperSeating(LocalDate date) {
		return multiDaySleeperBookings.get(date);
	}
	public BookedAndAvailableSeatsByDate getACSeating(LocalDate date) {
		return multiDayACBookings.get(date);
	}
	
	public void addBookedAndAvailableSeatsByDateStore (LocalDate localDate, BookedAndAvailableSeatsByDate seatallocationHelper ,int seatClass ) {
		if(seatClass ==1) {
			multiDaySleeperBookings.put(localDate, seatallocationHelper);
		}
		else {
			multiDayACBookings.put(localDate, seatallocationHelper);
		}
	}
	public BookedAndAvailableSeatsByDate getBookedAndAvailableSeatsByDateStore(LocalDate date, int seatingType) {
		BookedAndAvailableSeatsByDate bookedandAvailableSeats = null;
		if(seatingType ==1) {
			
		}
		return bookedandAvailableSeats;
	}
}
