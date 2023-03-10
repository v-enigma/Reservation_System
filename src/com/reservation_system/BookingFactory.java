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
	public Seat getSeat(Train train, int seatNo, int seatClass) {
		int coachCount = getCoachCount(train, seatClass);
		int seatsPerCoach = 0;
		Seat seat = null;
		if(seatClass == 1)
			seatsPerCoach = train.getSleeperSeating().getSeatsPerCoach();
		else{
			seatsPerCoach = train.getAcSeating().getSeatsPerCoach();
		}
		int coachId = seatNo/(coachCount * seatsPerCoach);

		int seatNoInCoach = seatNo%( seatsPerCoach);// edge case of 0 remainder has to be handled--- done

		if(seatNoInCoach ==0) {
			seatNoInCoach = seatsPerCoach;
			coachId--;
		}
		
		if(seatClass == 1) {

			seat = train.getSleeperSeating().getCoach(coachId).getSeat(seatNoInCoach-1);
		}
		else {
			seat = train.getAcSeating().getCoach(coachId).getSeat(seatNoInCoach-1);
		}

		return seat;
	}
	 public String getCoachName(Train train, int seatNo, int seatClass) {
		String coachName ="";
		
		if(seatClass == 1) {
			int seatPerCoach = train.getSleeperSeating().getSeatsPerCoach();
			int coachIndex =seatNo/seatPerCoach;
			if(seatNo%seatPerCoach == 0 )
				coachIndex--;
			Coach coach =train.getSleeperSeating().getCoach(coachIndex);
			coachName = coach.getName();
		}
		else {
			int seatPerCoach = train.getAcSeating().getSeatsPerCoach();
			int coachIndex = seatNo/seatPerCoach;
			Coach coach = train.getAcSeating().getCoach(coachIndex);
			coachName = coach.getName();
		}
		return coachName;
	}

	 
	public String createBooking(User user,List<UserDetails> passengers,Train train,LocalDate dateOfJourney,Station source, Station destination , int seatClass) {
		Booking booking =null;
		int seatNo = -1;
		String sourceCode = source.getId();
		String destinationCode = destination.getId();
		List<String> stopsCodes = train.getSchedule().getStopsCodes();
		Iterator<UserDetails>passengersIterator = passengers.iterator();
		List< Seat> allottedSeats = new ArrayList<>();
		List<BookingStatus> status = new ArrayList<>();
		List<String> coachNames = new ArrayList<>();
		List<UserDetails> seatAllottedPassengers = new ArrayList<>();
		long pnr = generateId();
		int passengerIndex = 0;
		Seat seat = null;

		//LocalDate trainStartDate = train.get
		while(passengersIterator.hasNext()) {

			UserDetails ud = passengersIterator.next();
			SeatType seatType = ud.getSeatPreference();
			String stringSeatNo = BookedAndAvailableSeatsByTrainAndDate.getInstance().getSeatForTrainAndDate(train,dateOfJourney,seatClass,seatType, sourceCode, destinationCode, stopsCodes);
			seat = null;
			if(stringSeatNo.charAt(0) == 'C'){
				seatNo = Integer.parseInt(stringSeatNo.substring(1));
				seat = getSeat(train, seatNo, seatClass);
				allottedSeats.add(seat);
				status.add(BookingStatus.CNF);
				String coach_Name = getCoachName(train, seatNo, seatClass);
				coachNames.add(coach_Name);
			}else if(stringSeatNo.charAt(0) == 'R'){
				seatNo = Integer.parseInt(stringSeatNo.substring(1));
				seat = getSeat(train, seatNo, seatClass);
				allottedSeats.add(seat);
				status.add(BookingStatus.RAC);
				String coach_Name = getCoachName(train, seatNo, seatClass);
				coachNames.add(coach_Name);
				BookingsData.getInstance().addRAC(pnr,passengerIndex,seatClass);

			}else if(stringSeatNo.charAt(0) == 'W'){
				int waitingIndex = Integer.parseInt(stringSeatNo.substring(1));
				allottedSeats.add(seat);
				status.add(BookingStatus.WL);
				coachNames.add(null);
				BookingsData.getInstance().addWaitingList(waitingIndex,pnr,passengerIndex,seatClass );
			}

			else if(stringSeatNo.charAt(0) == 'N') {
				break;
			}
			seatAllottedPassengers.add(ud);
		passengerIndex++;
		}
		if(allottedSeats.size()> 0 ) {
			booking = new Booking(user, seatAllottedPassengers, train, dateOfJourney, source, destination, pnr, allottedSeats, status, coachNames);
			addBooking(booking, user);
		}
		if(passengersIterator.hasNext() && allottedSeats.size() > 0  ) {
			String bookingDetailsAndSeatUnavailable = booking.toString();
			bookingDetailsAndSeatUnavailable+="\n FOR SOME PASSENGERS SEATS ARE NOT AVAILABLE . REGRET THE INCONVENIENCE\n";
			return bookingDetailsAndSeatUnavailable;
		}
		else if(!passengersIterator.hasNext() && status.contains(BookingStatus.WL)){
			String bookingDetailsAndWaitingList = booking.toString();
			bookingDetailsAndWaitingList+="\n PASSENGERS ARE ALLOTTED WAITING LIST \n ";
			return bookingDetailsAndWaitingList;
		}

		else if(passengersIterator.hasNext() && allottedSeats.size() == 0){
			return "\nSEATS ARE NOT AVAILABLE.REGRET THE INCONVENIENCE\n";
		}
		return booking.toString();
	}
	private void removeWaitingListBooking(long pnr, int index){
		BookingsData.getInstance().removeFromWaitingList(pnr, index);
		//Booking booking = BookingsData.getInstance().findBooking(pnr);
		//BookedAndAvailableSeatsByTrainAndDate.getInstance().freeSeat(booking.getSource().getId(), booking.getDestination().getId(),);
	}
	private void removeRAC(long pnr, int passengerIndex, int seatNo, int seatClass){
		BookingsData.getInstance().removeFromRAC(pnr, passengerIndex,seatClass);
		//BookingsData.getInstance().cancelBooking(pnr);
		Booking booking = BookingsData.getInstance().findBooking(pnr);
		BookedAndAvailableSeatsByTrainAndDate.getInstance().freeSeat(booking.getTrain(),booking.getJourneyDate(),seatClass,booking.getSource().getId(), booking.getDestination().getId(),seatNo);

	}
	private void freeConfirmedSeat(long pnr, int seatNo, int seatClass ){
		Booking booking = BookingsData.getInstance().findBooking(pnr);
		//BookedAndAvailableSeatsByTrainAndDate.getInstance().freeSeat(booking);
		BookedAndAvailableSeatsByTrainAndDate.getInstance().freeSeat(booking.getTrain(),booking.getJourneyDate(),seatClass,booking.getSource().getId(), booking.getDestination().getId(),seatNo);

	}
	public void cancelBooking(Long PNR, String UserId) { // have to add logic to update removed booking so that waiting list tickets status can be changed
		Booking booking = BookingsData.getInstance().findBooking(PNR);
		if(booking == null){
			System.out.println("No booking match the given PNR");
			return;
		}
		int seatClass = 0;
		if( booking.getCoachIds().get(0)!= null && booking.getCoachIds().get(0).charAt(0) == 'S'|| booking.getCoachIds().get(0).charAt(0)== 's')
		  seatClass = 1;
		else if( booking.getCoachIds().get(0)!= null && booking.getCoachIds().get(0).charAt(0) == 'A'|| booking.getCoachIds().get(0).charAt(0)== 'a')
			seatClass = 2;


		Customer user = (Customer) UsersData.getInstance().getUser(UserId);
		user.addCancelledBookings(PNR);
		//LocalDate date = booking.getJourneyDate();

		//List<BookingStatus> bs = booking.getStatus();
		Iterator<BookingStatus> statusIterator = booking.getStatus().iterator();
		Iterator<Seat> seatIterator = booking.getAllocatedSeats().iterator();
		int passengerIndex = 0;
		while(seatIterator.hasNext() && statusIterator.hasNext()){
			Seat seat = seatIterator.next();
			BookingStatus status = statusIterator.next();
			if(seat == null && status == BookingStatus.WL) {
				removeWaitingListBooking(PNR, passengerIndex);
			}
			if(seat != null ){
				switch (status){
					case RAC:
						removeRAC(PNR, passengerIndex, seat.getId(),seatClass);
						break;
					case CNF:
						freeConfirmedSeat(PNR, seat.getId(), seatClass);
						break;

				}
			}

			passengerIndex++;
		}
		BookingsData.getInstance().cancelBooking(PNR);
		findVacantSeatsForRAC(booking.getTrain().getId(),booking.getJourneyDate(), passengerIndex,seatClass);
		boolean hasChanged = BookingsData.getInstance().checkRACSeatAvailabilityForWaitingListBookings(booking.getTrain().getId(),booking.getJourneyDate(),seatClass);
		if(hasChanged){
			BookingsData.getInstance().updateWaitingListBookingStatus(booking.getTrain(),booking.getJourneyDate(),seatClass);
		}

	}
	private void findVacantSeatsForRAC(int trainNo, LocalDate dateOfJourney, int cancelledCount,int seatClass){
		BookingsData.getInstance().checkSeatAvailabilityForRACBookings(trainNo, dateOfJourney, cancelledCount, seatClass);

	}
	private void addBooking(Booking booking, User user) {
		BookingsData.getInstance().addBooking(booking);
		((Customer)(user)).addBooking(booking);
	}
	

	public BookedAndAvailableSeatsByDate  getBookedAndAvailableSeatsByDate(Train train, int seatClass) { // seating can be AC or sleeper
		BookedAndAvailableSeatsByDate seatAllocationHelper = null;
		int numCoaches =-1;
		int seatPerCoach = -1;
		if(seatClass == 1) {
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
	public void printTrainBookingsFromTrain(int trainNo, LocalDate dateOfJourney){
		List<Booking> bookings = BookingsData.getInstance().filterBookingsByTrainNoAndDate(trainNo, dateOfJourney);
		for(Booking booking : bookings){
			System.out.println(booking);
		}
	}
	public String filterBooking(Long PNR){
		Booking booking = BookingsData.getInstance().findBooking(PNR);
		if(booking!= null)
			return stringifyBooking(booking);
		return null;
	}
	 String stringifyBooking(Booking booking){

		List<String> coachIds = booking.getCoachIds();
		List<Seat> allocatedSeats = booking.getAllocatedSeats();
		List<BookingStatus>statusList = booking.getStatus();
		List<UserDetails> passenger = booking.getPassenger();
		StringBuilder bookingDetails = new StringBuilder();

		List<Record> waitingListRecords = null;
		Iterator<Record> waitingListIterator = null ;
		int i = 0;
		String journeyDetails =  "Source: "+ booking.getSource().getName() +" Destination : "+ booking.getDestination().getName()
				+ "   PNR : " + booking.getPNR()+"\t DateOFJourney :" + booking.getJourneyDate()+"\n"  ;
		bookingDetails.append(journeyDetails) ;
		for(BookingStatus status : statusList){
			String temp ="";
			if(status == BookingStatus.CNF || status == BookingStatus.RAC|| status == BookingStatus.CAN){
				temp = coachIds.get(i) + "\t\t\t" + allocatedSeats.get(i).getId() + "\t\t\t" + status + "\t\t\t" + passenger.get(i).getName() + "\n";
			}
			else if(status == BookingStatus.WL){
				if(waitingListRecords == null) {
					waitingListRecords = BookingsData.getInstance().findAllWaitingListRecordsOfABooking(booking.getPNR());
					waitingListIterator = waitingListRecords.iterator();
				}
				Record waitingRecord = waitingListIterator.next();
				temp = "--\t\t\t"+ waitingRecord.getcurrentNumber()+"\t\t\t"+ status + "\t\t\t"+ passenger.get(i).getName()+ "\n";

			}

			bookingDetails.append(temp);
			i++;
		}
		return bookingDetails.toString();
	}

}
