package com.Reservation_System;

public class BookingFactory {
	
	private static long id = 1000000;
	
	private Long generateId() {
    	return ++id;
    	
    }
	public Booking createBooking() {
		Booking booking = null;
		// logic to be filled
		return booking;
	}
}
