package com.database;

import java.time.LocalDate;
import java.util.*;

import com.reservation_system.*;
import com.enums.BookingStatus;

public class BookingsData {
	private final HashMap<Long, Booking > bookings = new HashMap<>();
	private static final BookingsData BookingData = new BookingsData();
	private final HashMap<Long, Booking> cancelledBookings = new HashMap<>();
	private final ArrayList<Record> racList = new ArrayList<>();
	private final ArrayList<Record> waitingList = new ArrayList<>();
	private BookingsData() {
		
	}
	public Record removeFromWaitingList(long pnr, int index){
		Iterator<Record> waitingListIterator = waitingList.iterator();
		while(waitingListIterator.hasNext()){
			Record waitingListRecord = waitingListIterator.next();
			if(waitingListRecord.getPNR() == pnr && waitingListRecord.getPassengerIndex() == index ) {
				waitingListIterator.remove();
				return waitingListRecord;
			}
		}
		return null;
	}

	public Record removeFromRAC(long pnr, int index, int seatClass){
		Iterator<Record> racIterator = racList.iterator();
		while(racIterator.hasNext()){
			Record RACRecord = racIterator.next();
			if(RACRecord.getPNR() == pnr && RACRecord.getPassengerIndex() == index && seatClass == RACRecord.getSeatClass()){
				racIterator.remove();

				return RACRecord;
			}

		}
		return null;
	}
	public void addRAC(long pnr, int passengerIndex, int seatClass){
		Record RACRecord = new Record(pnr, passengerIndex, seatClass);
		racList.add(RACRecord);
	}
	public void addWaitingList(int seatNo,long pnr, int passengerIndex,int seatClass){
		Record waitingRecord = new Record(seatNo,pnr, passengerIndex,seatClass);
		waitingList.add(waitingRecord);
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
			List<BookingStatus>seatStatus = booking.getStatus();
			int index = 0;
			while(index< allocatedSeats.size()) {

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
	private List<Record> filterByTrainAndDate(List<Record>recordList, int trainNo, LocalDate date,int seatClass){

		List<Record> matchedRecords = new ArrayList<>();
		for(Record record: recordList){
			long PNR = record.getPNR();
			if(bookings.containsKey(PNR) && bookings.get(PNR).getTrain().getId() == trainNo && bookings.get(PNR).getJourneyDate().equals(date) && record.getSeatClass() == seatClass){
				matchedRecords.add(record);
			}
		}
		return matchedRecords;
	}
	public void checkSeatAvailabilityForRACBookings(int trainNo, LocalDate dateOfJourney, int cancelledCount,int seatClass){

		List<Record> racBookingsForTrainOnADay = filterByTrainAndDate( racList,trainNo,dateOfJourney,seatClass);
		if(racBookingsForTrainOnADay.size() == 0)
			return;
		for(Record racRecord: racBookingsForTrainOnADay){

			Booking booking = null;
			long PNR = racRecord.getPNR();
			if(!bookings.containsKey(PNR)){
				return ;
			}
			booking = bookings.get(PNR);
			List<String>stopCodes = booking.getTrain().getSchedule().getStopsCodes();
			String sourceCode = booking.getSource().getId();
			String destinationCode = booking.getDestination().getId();

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
					removeFromRAC(racRecord.getPNR(),racRecord.getPassengerIndex(),seatClass);
					cancelledCount--;
				}

			}
			if(cancelledCount == 0){
				break; // created at most cancelledCount of empty seats because of cancellation .
					// if those no of seats are allotted  confirmed seats then there is no need to loop over here . So coming out of the loop.
			}
		}
		if(cancelledCount > 0){
			checkSeatAvailabilityForWaitingListBookings(trainNo,dateOfJourney, cancelledCount,seatClass);
		}

	}
	public void checkSeatAvailabilityForWaitingListBookings(int trainNo, LocalDate dateOfJourney, int cancelledCount,int seatClass){
		List<Record> matchedRecords = filterByTrainAndDate(waitingList,trainNo,dateOfJourney,seatClass );
		if(matchedRecords.size() == 0 )
			return;
		Iterator<Record> waitingListIterator = waitingList.iterator();
		while(waitingListIterator.hasNext()){
			Record waitingRecord = waitingListIterator.next();
			Booking booking = null;
			long PNR = waitingRecord.getPNR();
			if(!bookings.containsKey(PNR)){
				return ;
			}
			booking = bookings.get(PNR);
			List<String>stopCodes = booking.getTrain().getSchedule().getStopsCodes();
			String sourceCode = booking.getSource().getId();
			String destinationCode = booking.getDestination().getId();

			int seatNo = BookedAndAvailableSeatsByTrainAndDate.getInstance() .findSeatInBookedAndAvailableSeats(booking.getTrain().getId(),dateOfJourney,seatClass,
					booking.getPassenger().get(waitingRecord.getPassengerIndex()).getSeatPreference(),sourceCode, destinationCode,stopCodes);
			if(seatNo > 0 ){
				Seat seat = BookingFactory.getInstance().getSeat(booking.getTrain(),seatNo,seatClass);
				String coachId = BookingFactory.getInstance().getCoachName(booking.getTrain(), seatNo,seatClass);
				if(waitingRecord.getPassengerIndex() < booking.getCoachIds().size() && waitingRecord.getPassengerIndex() < booking.getAllocatedSeats().size()) {
					booking.getCoachIds().set(waitingRecord.getPassengerIndex(), coachId);
					booking.getStatus().set(waitingRecord.getPassengerIndex(),BookingStatus.CNF);
					booking.getAllocatedSeats().set(waitingRecord.getPassengerIndex(), seat);
					removeFromWaitingList(PNR,waitingRecord.getPassengerIndex());
					cancelledCount--;
				}

			}
			if(cancelledCount == 0){
				break;
			}

		}
	}
	public boolean checkRACSeatAvailabilityForWaitingListBookings(int trainNo, LocalDate dateOfJourney,int seatClass){
		boolean assigned = false;
		List<Record> matchedRecords = filterByTrainAndDate(waitingList,trainNo,dateOfJourney,seatClass );
		if(matchedRecords.size() == 0 )
			return assigned;

		for(Record waitingRecord: matchedRecords) {
			Booking booking = null;
			long PNR = waitingRecord.getPNR();
			if (!bookings.containsKey(PNR)) {
				return assigned;
			}
			booking = bookings.get(PNR);
			List<String> stopCodes = booking.getTrain().getSchedule().getStopsCodes();
			String sourceCode = booking.getSource().getId();
			String destinationCode = booking.getDestination().getId();

			int seatNo = BookedAndAvailableSeatsByTrainAndDate.getInstance().findRACSeatInBookedAndAvailableSeats(booking.getTrain().getId(),dateOfJourney,seatClass,
					booking.getPassenger().get(waitingRecord.getPassengerIndex()).getSeatPreference(),sourceCode, destinationCode,stopCodes);
			if(seatNo > 0 ){
				Seat seat = BookingFactory.getInstance().getSeat(booking.getTrain(),seatNo,seatClass);
				String coachId = BookingFactory.getInstance().getCoachName(booking.getTrain(), seatNo,seatClass);
				if(waitingRecord.getPassengerIndex() < booking.getCoachIds().size() && waitingRecord.getPassengerIndex() < booking.getAllocatedSeats().size()) {
					booking.getCoachIds().set(waitingRecord.getPassengerIndex(), coachId);
					booking.getStatus().set(waitingRecord.getPassengerIndex(),BookingStatus.RAC);
					booking.getAllocatedSeats().set(waitingRecord.getPassengerIndex(), seat);
					removeFromWaitingList(PNR,waitingRecord.getPassengerIndex()); // modifying a list while looping over the list cause exception 
					addRAC(PNR,waitingRecord.getPassengerIndex(), waitingRecord.getSeatClass());
					assigned = true;
				}

			}

		}
		return assigned;
	}
	public List<Record> findAllWaitingListRecordsOfABooking(Long PNR){
		List<Record> waitingListRecords = new ArrayList<>();
		for(Record waitingRecord: waitingList){
			if(waitingRecord.getPNR() == PNR ){
				waitingListRecords.add(waitingRecord);
			}
		}
		return waitingListRecords;
	}
	public List<Record> filterWaitingListByTrainAndDate(Train train, LocalDate dateOfJourney, int seatClass){
		List<Record> waitingListBookingsForATrainOnADate = new ArrayList<>();
		for(Record waitingRecord: waitingList){
			long pnr = waitingRecord.getPNR();
			if(bookings.get(pnr).getTrain().getId() == train.getId() && dateOfJourney == bookings.get(pnr).getJourneyDate() && seatClass == waitingRecord.getSeatClass()){
				waitingListBookingsForATrainOnADate.add(waitingRecord);
			}
		}
		return waitingListBookingsForATrainOnADate;
	}
	public void updateWaitingListBookingStatus(Train train, LocalDate dateOfJourney, int seatClass){
		List<Record> waitingListBookingsForATrainOnADate = filterWaitingListByTrainAndDate(train, dateOfJourney,seatClass);
		int currentIndex =0;
		for(Record waitingRecord : waitingListBookingsForATrainOnADate ){
			currentIndex++;
			waitingRecord.setCurrentNumber(currentIndex);
		}
		BookedAndAvailableSeatsByTrainAndDate.getInstance().getSeatStore(train, dateOfJourney,seatClass).setCurrentWL(currentIndex);

	}

}
