package com.database;

import java.time.LocalDate;
import java.util.*;

import com.reservation_system.Booking;
import com.reservation_system.Seat;
import com.enums.BookingStatus;

public class BookingsData {
	private final HashMap<Long, Booking > bookings = new HashMap<>();
	private static final BookingsData BookingData = new BookingsData();
	private HashMap<Long, Booking> cancelledBookings = new HashMap<>();
	private ArrayList<RACRecord> rac = new ArrayList<>();
	private ArrayList<RACRecord> waitingList = new ArrayList<>();
	private BookingsData() {
		
	}
	public RACRecord removeFromWaitingList(long pnr, int index){
		Iterator<RACRecord> waitingListIterator = waitingList.iterator();
		while(waitingListIterator.hasNext()){
			RACRecord RACRecord = waitingListIterator.next();
			if(RACRecord.getPNR() == pnr && RACRecord.getPassengerIndex() == index){
				waitingListIterator.remove();
				return RACRecord;
			}
		}
		return null;
	}
	public RACRecord removeFromRAC(long pnr, int index){
		Iterator<RACRecord> racIterator = rac.iterator();
		while(racIterator.hasNext()){
			RACRecord RACRecord = racIterator.next();
			if(RACRecord.getPNR() == pnr && RACRecord.getPassengerIndex() == index){
				racIterator.remove();
				return RACRecord;
			}

		}
		return null;
	}
	public void addRAC(long pnr, int passengerIndex){
		RACRecord RACRecord = new RACRecord(pnr, passengerIndex);
		rac.add(RACRecord);
	}
	public void addWaitingList(long pnr, int passengerIndex){
		RACRecord RACRecord = new RACRecord(pnr, passengerIndex);
		waitingList.add(RACRecord);
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
		if(booking!= null) {
			LocalDate journeyData = booking.getJourneyDate();
			if (journeyData.isBefore(LocalDate.now()))

				return null;

			else
				return booking;
		}
		return null;
		
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
	public List<Booking> filterBookingsByTrainNoAndDate(int trainNo, LocalDate dateOfJourney){
		List<Booking> matchedBookings = new ArrayList<>();
		Iterator bookingsIterator = bookings.entrySet().iterator();
		while(bookingsIterator.hasNext()) {
			Map.Entry<Long, Booking> bookingRecord = (Map.Entry<Long,Booking>) bookingsIterator.next();
			if (bookingRecord.getValue().getTrain().getId() == trainNo && bookingRecord.getValue().getJourneyDate().equals(dateOfJourney)) {
				matchedBookings.add(bookingRecord.getValue());
				}
			}
			return matchedBookings;


	}
}
