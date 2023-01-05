package com.reservation_system;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.database.BookedAndAvailableSeatsByDate;
import com.database.BookedAndAvailableSeatsByTrainAndDate;
import com.database.BookingsData;
import com.database.MultiDayBookingStore;
import com.database.UsersData;
import com.enums.BookingStatus;
import com.enums.SeatType;


public class BookingFactory {
	private static final BookingFactory BOOKING_Factory = new BookingFactory(); 
	private static long id = 1000000;
	
	private Long generateId() {
    	return ++id;
    	
    }
	public static BookingFactory getInstance() {
		return BOOKING_Factory;
	}
	private int getCoachCount(Train train, int seatClass) {
		if(seatClass == 1) {
			return train.getSleeperSeating().getNumCoaches();
		}
		else
			return train.getAcSeating().getNumCoaches();
	}
	private Seat getSeat(Train train, int seatNo, int seatClass) {
		int coachCount = getCoachCount(train, seatClass);
		int coachId = seatNo/coachCount;
		coachId++;
		int seatNoInCoach = seatNo%coachCount;// edge case of 0 remainder has to be handled
		
		Seat seat = null;
		
		if(seatClass == 1) {
			seat =train.getSleeperSeating().getCoach(coachId).getSeat(seatNoInCoach);
		}
		else {
			seat = train.getAcSeating().getCoach(coachId).getSeat(seatNoInCoach);
		}
		return seat;
	}
	private String getCoachName(Train train, int seatNo, int seatClass) {
		String coachName ="";
		
		if(seatClass == 1) {
			int seatPerCoach = train.getSleeperSeating().getSeatsPerCoach();
			int coachIndex =seatNo%seatPerCoach;
			Coach coach =train.getSleeperSeating().getCoach(coachIndex);
			coachName = coach.getName();
		}
		else {
			int seatPerCoach = train.getAcSeating().getSeatsPerCoach();
			int coachIndex = seatNo%seatPerCoach;
			Coach coach = train.getAcSeating().getCoach(coachIndex);
			coachName = coach.getName();
		}
		return coachName;
	}
	private int getStopIndex(Train train,Station station) {
		List<String> stops = train.getSchedule().getStops();
		Iterator<String>stopsIterator = stops.iterator();
		int stopIndex = -1;
		int index = 0;
		while( stopsIterator.hasNext()) {
			String stationName = stopsIterator.next();
			if(stationName.equalsIgnoreCase(station.getName()))
				stopIndex = index; 
			
			index++;
		}
		return stopIndex;
	}
	//Train train, LocalDate dateOfJourney, int seatClass, SeatType seatType, int sourceIndex, int destinationIndex  ,int trainDestinationIndex
	//Customer customer, List<UserDetails> passenger, Train train, LocalDate dJ, Station source, Station destination, int PNR,List<Seat> seatsAllocated, List<Integer>coachIds
	public Booking createBooking(User user,List<UserDetails> passengers,Train train,LocalDate dateOfJourney,Station source, Station destination , int seatClass) {
		Booking booking =null;
		
		int sourceIndex =getStopIndex(train,source);
		int destinationIndex = getStopIndex(train, destination);
		int trainDestinationIndex = train.getSchedule().getStops().size()-1;
		
		
		
		Iterator<UserDetails>passengersIterator = passengers.iterator();
		List< Seat> allotedSeats = new ArrayList<>();
		List<BookingStatus> status = new ArrayList<>();
		List<String> coachNames = new ArrayList<>();
		while(passengersIterator.hasNext()) {
			UserDetails ud = passengersIterator.next();
			SeatType seatType = ud.getSeatPreference();
			Integer seatNo = BookedAndAvailableSeatsByTrainAndDate.getInstance().getSeatForTrainAndDate(train,dateOfJourney,seatClass,seatType, sourceIndex, destinationIndex, trainDestinationIndex);
			Seat seat = getSeat(train, seatNo, seatClass);
			allotedSeats.add(seat);
			if(seat== null) {
				status.add(BookingStatus.WL);
				coachNames.add(null);
			}
			else {
				status.add(BookingStatus.CNF);
				String coach_Name = getCoachName(train, seatNo, seatClass);
				coachNames.add(coach_Name);
			}
				
			
		}
		booking = new Booking(user,passengers,train,dateOfJourney,source,destination,generateId(),allotedSeats,status,coachNames);
		addBooking(booking, user);
		return booking;
	}
	public void cancelBooking(Long PNR, String UserId) { // have to add logic to update removed booking so that waiting list tickets status can be changed
		Booking booking =BookingsData.getInstance().cancelBooking(PNR); 
		Customer user =(Customer) UsersData.getInstance().getUser(UserId);
		user.addCancelledBookings(PNR);
	}
	private void addBooking(Booking booking, User user) {
		BookingsData.getInstance().addBooking(booking);
	}
	
	public void getBookings(String userId) {
		Customer user = (Customer) UsersData.getInstance().getUser(userId);
		List<Booking> bookings =user.getBookings();
		
	}
	public BookedAndAvailableSeatsByDate  getBookedAndAvailableSeatsByDate(Train train, int seatClass) { // seating can be AC or sleeper
		BookedAndAvailableSeatsByDate seatAllocationHelper = null;
		int numCoaches =-1;
		int seatPerCoach = -1;
		if(seatClass ==1) {
			 numCoaches = train.getSleeperSeating().getNumCoaches();
			 seatPerCoach = train.getSleeperSeating().getSeatsPerCoach();
			
		}
		else {
			numCoaches = train.getAcSeating().getNumCoaches();
			seatPerCoach = train.getAcSeating().getSeatsPerCoach();
			
		}
		seatAllocationHelper = new BookedAndAvailableSeatsByDate(numCoaches, seatPerCoach);
		return seatAllocationHelper;
	}
	public MultiDayBookingStore getMultiDayBookingStoreInstance() {
		MultiDayBookingStore multiDayBookingStore = new MultiDayBookingStore();
		return multiDayBookingStore;
	}
}
