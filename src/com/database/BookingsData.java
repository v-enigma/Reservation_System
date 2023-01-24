package com.database;

import java.time.LocalDate;
import java.util.*;

import com.reservation_system.Booking;
import com.reservation_system.BookingFactory;
import com.reservation_system.Seat;
import com.enums.BookingStatus;

public class BookingsData {
	private final HashMap<Long, Booking > bookings = new HashMap<>();
	private static final BookingsData BookingData = new BookingsData();
	private final HashMap<Long, Booking> cancelledBookings = new HashMap<>();
	private final ArrayList<RACRecord> racList = new ArrayList<>();
	private final ArrayList<RACRecord> waitingList = new ArrayList<>();
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
		Iterator<RACRecord> racIterator = racList.iterator();
		while(racIterator.hasNext()){
			RACRecord RACRecord = racIterator.next();
			if(RACRecord.getPNR() == pnr && RACRecord.getPassengerIndex() == index){
				racIterator.remove();
				return RACRecord;
			}

		}
		return null;
	}
	public void addRAC(long pnr, int passengerIndex, int seatClass){
		RACRecord RACRecord = new RACRecord(pnr, passengerIndex, seatClass);
		racList.add(RACRecord);
	}
	public void addWaitingList(long pnr, int passengerIndex,int seatClass){
		RACRecord RACRecord = new RACRecord(pnr, passengerIndex,seatClass);
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
			List<Seat> allocatedSeats = booking.getAllocatedSeats();
			List<String>coachNames = booking.getCoachIds();
			List<BookingStatus>seatStatus = booking.getStatus();
			int index = 0;
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
	private List<RACRecord> filterByTrainAndDate(List<RACRecord>racList, int trainNo, LocalDate date){

		List<RACRecord> matchedRecords = new ArrayList<>();
		for(RACRecord racRecord: racList){
			long PNR = racRecord.getPNR();
			if(bookings.containsKey(PNR) && bookings.get(PNR).getTrain().getId() == trainNo && bookings.get(PNR).getJourneyDate().equals(date)){
				matchedRecords.add(racRecord);
			}
		}
		return matchedRecords;
	}
	public void checkSeatAvailabilityForRACBookings(int trainNo, LocalDate dateOfJourney, int cancelledCount){

		List<RACRecord> racBookingsForTrainOnADay = filterByTrainAndDate( racList,trainNo,dateOfJourney);
		if(racBookingsForTrainOnADay.size() == 0)
			return;
		for(RACRecord racRecord: racBookingsForTrainOnADay){

			Booking booking = null;
			long PNR = racRecord.getPNR();
			if(!bookings.containsKey(PNR)){
				return ;
			}
			booking = bookings.get(PNR);
			List<String>stopCodes = booking.getTrain().getSchedule().getStopsCodes();
			String sourceCode = booking.getSource().getId();
			String destinationCode = booking.getDestination().getId();
			int seatClass = racRecord.getSeatClass();
			int seatNo = BookedAndAvailableSeatsByTrainAndDate.getInstance() .findSeatInBookedAndAvailableSeats(booking.getTrain().getId(),dateOfJourney,seatClass,
					booking.getPassenger().get(racRecord.getPassengerIndex()).getSeatPreference(),sourceCode, destinationCode,stopCodes);
			if(seatNo > 0 ){
				Seat seat = BookingFactory.getInstance().getSeat(booking.getTrain(),seatNo,seatClass);
				String coachId = BookingFactory.getInstance().getCoachName(booking.getTrain(), seatNo,seatClass);
				if(racRecord.getPassengerIndex() < booking.getCoachIds().size() && racRecord.getPassengerIndex() < booking.getAllocatedSeats().size()) {
					booking.getCoachIds().set(racRecord.getPassengerIndex(), coachId);
					booking.getStatus().set(racRecord.getPassengerIndex(),BookingStatus.CNF);
					Seat freedRACSeat = booking.getAllocatedSeats().set(racRecord.getPassengerIndex(), seat);
					BookedAndAvailableSeatsByTrainAndDate.getInstance().freeSeat(booking.getTrain(),dateOfJourney,seatClass,sourceCode,destinationCode,freedRACSeat.getId());
					cancelledCount--;
				}

			}
			if(cancelledCount == 0){
				break; // created at most cancelledCount of empty seats because of cancellation .
					// if those no of seats are allotted  confirmed seats then there is no need to loop over here . So coming out of the loop.
			}
		}
		if(cancelledCount > 0){
			checkSeatAvailabilityForWaitingListBookings(trainNo,dateOfJourney, cancelledCount);
		}

	}
	public void checkSeatAvailabilityForWaitingListBookings(int trainNo, LocalDate dateOfJourney, int cancelledCount){
		List<RACRecord> matchedRecords = filterByTrainAndDate(waitingList,trainNo,dateOfJourney );
		if(matchedRecords.size() == 0 )
			return;
		for(RACRecord waitingRecord: waitingList){
			Booking booking = null;
			long PNR = waitingRecord.getPNR();
			if(!bookings.containsKey(PNR)){
				return ;
			}
			booking = bookings.get(PNR);
			List<String>stopCodes = booking.getTrain().getSchedule().getStopsCodes();
			String sourceCode = booking.getSource().getId();
			String destinationCode = booking.getDestination().getId();
			int seatClass = waitingRecord.getSeatClass();

			int seatNo = BookedAndAvailableSeatsByTrainAndDate.getInstance() .findSeatInBookedAndAvailableSeats(booking.getTrain().getId(),dateOfJourney,seatClass,
					booking.getPassenger().get(waitingRecord.getPassengerIndex()).getSeatPreference(),sourceCode, destinationCode,stopCodes);
			if(seatNo > 0 ){
				Seat seat = BookingFactory.getInstance().getSeat(booking.getTrain(),seatNo,seatClass);
				String coachId = BookingFactory.getInstance().getCoachName(booking.getTrain(), seatNo,seatClass);
				if(waitingRecord.getPassengerIndex() < booking.getCoachIds().size() && waitingRecord.getPassengerIndex() < booking.getAllocatedSeats().size()) {
					booking.getCoachIds().set(waitingRecord.getPassengerIndex(), coachId);
					booking.getStatus().set(waitingRecord.getPassengerIndex(),BookingStatus.CNF);
					booking.getAllocatedSeats().set(waitingRecord.getPassengerIndex(), seat);
					cancelledCount--;
				}

			}
			if(cancelledCount == 0){
				break;
			}

		}
	}
	public void checkRACSeatAvailabilityForWaitingListBookings(int trainNo, LocalDate dateOfJourney){
		List<RACRecord> matchedRecords = filterByTrainAndDate(waitingList,trainNo,dateOfJourney );
		if(matchedRecords.size() == 0 )
			return;
		for(RACRecord waitingRecord: waitingList) {
			Booking booking = null;
			long PNR = waitingRecord.getPNR();
			if (!bookings.containsKey(PNR)) {
				return;
			}
			booking = bookings.get(PNR);
			List<String> stopCodes = booking.getTrain().getSchedule().getStopsCodes();
			String sourceCode = booking.getSource().getId();
			String destinationCode = booking.getDestination().getId();
			int seatClass = waitingRecord.getSeatClass();
			int seatNo = BookedAndAvailableSeatsByTrainAndDate.getInstance().findRACSeatInBookedAndAvailableSeats(booking.getTrain().getId(),dateOfJourney,seatClass,
					booking.getPassenger().get(waitingRecord.getPassengerIndex()).getSeatPreference(),sourceCode, destinationCode,stopCodes);
			if(seatNo > 0 ){
				Seat seat = BookingFactory.getInstance().getSeat(booking.getTrain(),seatNo,seatClass);
				String coachId = BookingFactory.getInstance().getCoachName(booking.getTrain(), seatNo,seatClass);
				if(waitingRecord.getPassengerIndex() < booking.getCoachIds().size() && waitingRecord.getPassengerIndex() < booking.getAllocatedSeats().size()) {
					booking.getCoachIds().set(waitingRecord.getPassengerIndex(), coachId);
					booking.getStatus().set(waitingRecord.getPassengerIndex(),BookingStatus.RAC);
					booking.getAllocatedSeats().set(waitingRecord.getPassengerIndex(), seat);
				}

			}

		}
	}
}
