package com.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.reservation_system.Booking;
import com.reservation_system.Seat;
import com.enums.BookingStatus;

public class BookingsData {
	private final HashMap<Long, Booking > bookings = new HashMap<>();
	private static final BookingsData BookingData = new BookingsData();
	private HashMap<Long, Booking> cancelledBookings = new HashMap<>();
	private ArrayList<Node> rac =new ArrayList<>();
	private ArrayList<Node> waitingList = new ArrayList<>();
	private BookingsData() {
		
	}
	public Node removeFromWaitingList(long pnr, int index){
		Iterator<Node> waitingListIterator = waitingList.iterator();
		while(waitingListIterator.hasNext()){
			Node node = waitingListIterator.next();
			if(node.getPNR() == pnr && node.getPassengerIndex() == index){
				waitingListIterator.remove();
				return node;
			}
		}
		return null;
	}
	public Node removeFromRAC(long pnr, int index){
		Iterator<Node> racIterator = rac.iterator();
		while(racIterator.hasNext()){
			Node node = racIterator.next();
			if(node.getPNR() == pnr && node.getPassengerIndex() == index){
				racIterator.remove();
				return node;
			}

		}
		return null;
	}
	public void addRAC(long pnr, int passengerIndex){
		Node node = new Node(pnr, passengerIndex);
		rac.add(node);
	}
	public void addWaitingList(long pnr, int passengerIndex){
		Node node = new Node(pnr, passengerIndex);
		waitingList.add(node);
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
}
