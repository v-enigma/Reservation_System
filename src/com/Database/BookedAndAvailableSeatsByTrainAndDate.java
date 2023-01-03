package com.Database;

import java.time.LocalDate;
import java.util.HashMap;

import com.Reservation_System.BookingFactory;
import com.Reservation_System.Train;



public class BookedAndAvailableSeatsByTrainAndDate {
	
	private final static BookedAndAvailableSeatsByTrainAndDate allocatedSeatsByTrain = new BookedAndAvailableSeatsByTrainAndDate();
	private final HashMap<Integer,MultiDayBookingStore > trainBookings = new HashMap<>(); //stores train Id as key and multiple days ahead booking as value
	
	public static BookedAndAvailableSeatsByTrainAndDate getInstance() {
		return allocatedSeatsByTrain;
	}
	private BookedAndAvailableSeatsByTrainAndDate() {
		
	}
     
	public void addBooking(Train train, LocalDate dateOfJounery, int seatingType) {
		int trainNo = train.getId();
		if(!trainBookings.containsKey(trainNo)) 
			trainBookings.put(trainNo, new MultiDayBookingStore());
		if(seatingType == 1 )
			if(trainBookings.get(trainNo).getSleeperSeating(dateOfJounery) == null) {
				
				BookedAndAvailableSeatsByDateStore seatAllocationHelper = BookingFactory.getInstance().getSeatAllocationHelper(train, seatingType);
				trainBookings.get(trainNo).addSeatAllocationHelper(dateOfJounery, seatAllocationHelper );
			}
			else {
				
			}
	}
	
	
	
    
	/*
	 * public static void main(String[] args) { BookingDB b = new BookingDB();
	 * System.out.println(b.generateId());
	 * 
	 * }
	 */
}
