package com.database;

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
	
	public void addBookedAndAvailableSeatsByDateStore (LocalDate localDate, BookedAndAvailableSeatsByDate seatAllocationHelper ,int seatClass ) {
		if(seatClass ==1) {
			multiDaySleeperBookings.put(localDate, seatAllocationHelper);
		}
		else {
			multiDayACBookings.put(localDate, seatAllocationHelper);
		}
	}
	public BookedAndAvailableSeatsByDate getBookedAndAvailableSeatsByDateStore(LocalDate date, int seatClass) {
		BookedAndAvailableSeatsByDate bookedAndAvailableSeats = null;
		if(seatClass ==1) {
			
		}
		return bookedAndAvailableSeats;
	}
	public void freeSeat(LocalDate dateOfJourney, int seatClass , String sourceCode, String destinationCode, int seatNo){
		if(seatClass == 1){
			multiDaySleeperBookings.get(dateOfJourney).freeSeat( sourceCode, destinationCode, seatNo);
		}
		else{
			multiDayACBookings.get(dateOfJourney).freeSeat(sourceCode, destinationCode, seatNo);
		}
	}
}
