package com.Database;

import java.time.LocalDate;
import java.util.HashMap;

import com.Reservation_System.MultiDayBookingStore;



public class BookingsData {
	
	private final static BookingsData BOOKING_DB = new BookingsData();
	private final HashMap<Integer,MultiDayBookingStore > trainBookings = new HashMap<>(); //stores train Id as key and multiple days ahead booking as value
	
	public static BookingsData getInstance() {
		return BOOKING_DB;
	}
	private BookingsData() {
		
	}
     
	public void addBooking(Integer trainNo, LocalDate dateOfJounery, int bookingClass) {
		if(!trainBookings.containsKey(trainNo)) 
			trainBookings.put(trainNo, new MultiDayBookingStore());
		if(bookingClass == 1 )
			if(trainBookings.get(trainNo).getSleeperSeating(dateOfJounery) == null) {
				trainBookings.get(trainNo).addSeatAllocationHelper(dateOfJounery,  );
			}
	}
	
	
	
    
	/*
	 * public static void main(String[] args) { BookingDB b = new BookingDB();
	 * System.out.println(b.generateId());
	 * 
	 * }
	 */
}
