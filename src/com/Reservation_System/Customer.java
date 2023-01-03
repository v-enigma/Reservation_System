package com.Reservation_System;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer extends User {

	private final List<Booking> confirmedBookings = new ArrayList<>();
	private final List<Booking> cancelledBookings = new ArrayList<>();

	Customer(String id, String phoneNumber,  String mailId,UserDetails userDetails) {
		super(id, phoneNumber, mailId,userDetails);
		// TODO Auto-generated constructor stub
		
	}
	public List<Booking> getBookings(){
		return confirmedBookings;
	}
	public List<Booking> getCancelledBookings(){
		return cancelledBookings;
	}
	public void addBooking(Booking booking) {
		confirmedBookings.add(booking);
	}
	public void addCancelledBookings(long PNR) {
		Booking booking = getBookingById(PNR, confirmedBookings);
		if(booking!= null)
			cancelledBookings.add(booking);
		
	}
	void RemoveBookings(List<Booking> bookingList){  // access specifier default
		boolean foundFirst =false;
		Iterator<Booking> bookings = bookingList.iterator();
		while(bookings.hasNext()) {
			
			LocalDate DateOfJourney = bookings.next().getjourneyDate();
			LocalDate today = LocalDate.now();
			if(foundFirst || ChronoUnit.DAYS.between(today, DateOfJourney)>=60) {
				foundFirst = true;
				bookings.remove();
				
			}
		}
		
	}
	private Booking getBookingById(long PNR, List<Booking> bookings) {
		Booking booking = null;
		boolean found =false;
		Iterator<Booking> bookingIterator = bookings.iterator();
		while(bookingIterator.hasNext()) {
			booking = bookingIterator.next();
			if(booking.getPNR() == PNR) {
				bookingIterator.remove();
				found = true;
				break;
			}
				
		}
		if(!found)
			booking = null;
		return booking;
	}

}
