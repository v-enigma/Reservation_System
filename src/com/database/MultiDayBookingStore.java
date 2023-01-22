package com.database;

import com.reservation_system.Train;

import java.time.LocalDate;
import java.util.HashMap;

public class MultiDayBookingStore { // 120 Days Ahead occupied seats and unoccupied seats  Including Today storage 
	public MultiDayBookingStore(){
		
	}
	private final  HashMap<LocalDate ,BookedAndAvailableSeatsByDate >multiDaySleeperBookings = new HashMap<>();
	private final HashMap<LocalDate, BookedAndAvailableSeatsByDate> multiDayACBookings= new HashMap<>();
	
	public BookedAndAvailableSeatsByDate getSleeperSeating(Train train , LocalDate date) {

		if(!multiDaySleeperBookings.containsKey(date))
			multiDaySleeperBookings.put(date, createBookedAndAvailableSeatsByDate(train.getSleeperSeating().getNumCoaches(), train.getSleeperSeating().getSeatsPerCoach()));
		if(multiDaySleeperBookings.get(date) == null) {
			BookedAndAvailableSeatsByDate bookedAndAvailableSeatsByDate = createBookedAndAvailableSeatsByDate(train.getSleeperSeating().getNumCoaches(), train.getSleeperSeating().getSeatsPerCoach());
			multiDaySleeperBookings.put(date,bookedAndAvailableSeatsByDate );
		}
		return multiDaySleeperBookings.get(date);

		}

	public BookedAndAvailableSeatsByDate getACSeating(Train train, LocalDate date) {

		if(!multiDayACBookings.containsKey(date))
			multiDayACBookings.put(date, createBookedAndAvailableSeatsByDate(train.getSleeperSeating().getNumCoaches(), train.getAcSeating().getSeatsPerCoach()));
		if(multiDayACBookings.get(date)!= null) {
			BookedAndAvailableSeatsByDate bookedAndAvailableSeatsByDate = createBookedAndAvailableSeatsByDate(train.getAcSeating().getNumCoaches(), train.getAcSeating().getSeatsPerCoach());
		}

		return multiDayACBookings.get(date);
	}
	
	public void addBookedAndAvailableSeatsByDateStore (LocalDate localDate, BookedAndAvailableSeatsByDate seatAllocationHelper ,int seatClass ) {
		if(seatClass == 1) {
			multiDaySleeperBookings.put(localDate, seatAllocationHelper);
		}
		else {
			multiDayACBookings.put(localDate, seatAllocationHelper);
		}
	}
	public BookedAndAvailableSeatsByDate getBookedAndAvailableSeatsByDateStore(LocalDate date, int seatClass) {
		BookedAndAvailableSeatsByDate bookedAndAvailableSeats = null;
		if(seatClass == 1) {
			bookedAndAvailableSeats = multiDaySleeperBookings.get(date);
		}
		else
			bookedAndAvailableSeats = multiDayACBookings.get(date);
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
	public BookedAndAvailableSeatsByDate createBookedAndAvailableSeatsByDate(int coachCount , int seatsInCoach){
		BookedAndAvailableSeatsByDate bookedAndAvailableSeatsByDate = new BookedAndAvailableSeatsByDate(coachCount, seatsInCoach);
		return bookedAndAvailableSeatsByDate;
	}
}
