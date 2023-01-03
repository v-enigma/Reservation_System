package com.Reservation_System;

import java.time.LocalDate;
import java.util.List;

import com.Database.BookedAndAvailableSeatsByDateStore;
import com.Database.MultiDayBookingStore;
import com.Reservation_System.Enum.SeatType;

public class BookingFactory {
	private static final BookingFactory BOOKING_Factory = new BookingFactory(); 
	private static long id = 1000000;
	
	private Long generateId() {
    	return ++id;
    	
    }
	public static BookingFactory getInstance() {
		return BOOKING_Factory;
	}
	//Customer customer, List<UserDetails> passenger, Train train, LocalDate dJ, Station source, Station destination, int PNR,List<Seat> seatsAllocated, List<Integer>coachIds
	public Booking createBooking(User user,Train train,List<UserDetails> passengers, int source, int destination, int trainDestination) {
		Booking booking = null;
		BookingsData.getInstance().
		
		booking = new (generateId());
		
		return booking;
	}
	public BookedAndAvailableSeatsByDateStore  getSeatAllocationHelper(Train train, int seatingType) { // seating can be AC or sleeper
		BookedAndAvailableSeatsByDateStore seatAllocationHelper = null;
		if(seatingType ==1) {
			int numCoaches = train.getSleeperSeating().getNumCoaches();
			int seatPerCoach = train.getSleeperSeating().getSeatsPerCoach();
			seatAllocationHelper = new BookedAndAvailableSeatsByDateStore(numCoaches, seatPerCoach);
		}
		else {
			int numCoaches = train.getAcSeating().getNumCoaches();
			int seatPerCoach = train.getSleeperSeating().getSeatsPerCoach();
			seatAllocationHelper = new BookedAndAvailableSeatsByDateStore(numCoaches,seatPerCoach);
		}
		return seatAllocationHelper;
	}
	public MultiDayBookingStore getMultiDayBookingStoreInstance() {
		MultiDayBookingStore multiDayBookingStore = new MultiDayBookingStore();
		return multiDayBookingStore;
	}
}
