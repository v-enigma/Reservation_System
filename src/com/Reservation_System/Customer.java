package com.Reservation_System;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer extends User {

	private final List<Booking> confirmedbookings = new ArrayList<>();
	private final List<Booking> cancelledBookings = new ArrayList<>();

	Customer(String id, String phoneNumber,  String mailId,UserDetails userDetails) {
		super(id, phoneNumber, mailId,userDetails);
		// TODO Auto-generated constructor stub
		
	}
	public List<Booking> getBookings(){
		return confirmedbookings;
	}
	public List<Booking> getCancelledBookings(){
		return cancelledBookings;
	}
	public void addBooking(Booking booking) {
		confirmedbookings.add(booking);
	}
	public void addCancelledBookings(Booking booking) {
		cancelledBookings.add(booking);
	}
	void RemoveBookings(List<Booking> bookingList){  // access specifier default
		boolean foundFirst =false;
		Iterator<Booking> bookings = bookingList.iterator();
		while(bookings.hasNext()) {
			
			LocalDateTime DateOfJourney = bookings.next().getDateOfJourney();
			LocalDateTime today = LocalDateTime.now();
			if(foundFirst || ChronoUnit.DAYS.between(today, DateOfJourney)>=60) {
				foundFirst = true;
				bookings.remove();
				
			}
		}
		
	}

}
