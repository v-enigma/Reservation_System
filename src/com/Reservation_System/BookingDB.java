package com.Reservation_System;

public class BookingDB {
	private static long id = 1000000;
	private static final BookingDB BOOKING_DB = new BookingDB();
	
	public static BookingDB getInstance() {
		return BOOKING_DB;
	}
	private BookingDB() {
		
	}
    public Long generateId() {
    	return ++id;
    	
    }
    
	/*
	 * public static void main(String[] args) { BookingDB b = new BookingDB();
	 * System.out.println(b.generateId());
	 * 
	 * }
	 */
}
