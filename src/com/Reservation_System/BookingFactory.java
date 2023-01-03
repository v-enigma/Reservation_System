package com.Reservation_System;

import java.time.LocalDate;
import java.util.List;

import com.Database.BookingsData;
import com.Reservation_System.Enum.SeatType;

public class BookingFactory {
	private static final BookingFactory BOOKING_DB = new BookingFactory(); 
	private static long id = 1000000;
	
	private Long generateId() {
    	return ++id;
    	
    }
	public static BookingFactory getInstance() {
		return BOOKING_DB;
	}
	//Customer customer, List<UserDetails> passenger, Train train, LocalDate dJ, Station source, Station destination, int PNR,List<Seat> seatsAllocated, List<Integer>coachIds
	public Booking createBooking(User user,Train train,List<UserDetails> passengers, int source, int destination, int trainDestination) {
		Booking booking = null;
		BookingsData.getInstance().
		booking = new (generateId())
		
		return booking;
	}
	
}
