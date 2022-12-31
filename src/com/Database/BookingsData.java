package com.Database;

public class BookingsData {
	
	private static final BookingsData BOOKING_DB = new BookingsData();
	
	public static BookingsData getInstance() {
		return BOOKING_DB;
	}
	private BookingsData() {
		
	}
    
    
	/*
	 * public static void main(String[] args) { BookingDB b = new BookingDB();
	 * System.out.println(b.generateId());
	 * 
	 * }
	 */
}
