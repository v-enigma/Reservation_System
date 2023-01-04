package com.Database;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.Reservation_System.Booking;
import com.Reservation_System.Seat;
import com.Reservation_System.Enum.BookingStatus;

public class BookingsData {
	private HashMap<Long, Booking > bookings = new HashMap<>();
	private static final BookingsData BookingData = new BookingsData();
	private HashMap<Long, Booking> cancelledBookings = new HashMap<>();
	private BookingsData() {
		
	}
	public static BookingsData getInstance() {
		return BookingData;
	}
	public void addBooking(Booking booking) {
		if(bookings.containsKey(booking.getPNR())) {
			System.out.println(" PNR  Error");
		}
		else {
			bookings.put(booking.getPNR(), booking);
		}
	}
	public Booking findBooking(Long PNR) {
		Booking booking = bookings.get(PNR);
		LocalDate journeyData = booking.getjourneyDate();
		if(journeyData.isBefore(LocalDate.now())) 
			
			return null;
		
		else 
			return booking;
		
	}
	public Booking cancelBooking(Long PNR) { 
	// has to add logic related to moving seat form booked to available and move the seat from bookings to cancelled bookings in customer
		Booking booking = null;
		if(bookings.containsKey(PNR)) {
			 booking = bookings.remove(PNR);
			List<Seat> allocatedSeats =booking.getAllocatedSeats();
			List<String>coachNames = booking.getCoachIds();
			List<BookingStatus>seatStatus = booking.getStatus();
			int index =0;
			while(index< allocatedSeats.size()) {
				allocatedSeats.set(index, null);
				coachNames.set(index, null);
				seatStatus.set(index, BookingStatus.CAN);
				index++;
			}
			cancelledBookings.put(PNR,booking);
		}
		return booking;
	}
}
