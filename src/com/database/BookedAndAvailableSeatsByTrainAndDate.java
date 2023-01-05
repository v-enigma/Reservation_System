package com.database;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.reservation_system.BookingFactory;
import com.reservation_system.Train;
import com.enums.SeatType;



public class BookedAndAvailableSeatsByTrainAndDate {
	
	private final static BookedAndAvailableSeatsByTrainAndDate allocatedSeatsByTrain = new BookedAndAvailableSeatsByTrainAndDate();
	private final Map<Integer,MultiDayBookingStore > trainBookings = new HashMap<>(); //stores train Id as key and multiple days ahead booking as value
	
	public static BookedAndAvailableSeatsByTrainAndDate getInstance() {
		return allocatedSeatsByTrain;
	}
	private BookedAndAvailableSeatsByTrainAndDate() {
		
	}
    private void ensureSeatStoreExists(Train train, LocalDate dateOfJourney,int seatClass) {
    	int trainNo = train.getId();
    	if(!trainBookings.containsKey(trainNo))
    		trainBookings.put(trainNo,BookingFactory.getInstance().getMultiDayBookingStoreInstance() );
    	BookedAndAvailableSeatsByDate seatStore = getSeatStore(train,dateOfJourney, seatClass);
		if(seatStore == null) {
			BookedAndAvailableSeatsByDate seatAllocationHelper = BookingFactory.getInstance().getBookedAndAvailableSeatsByDate(train, seatClass);
			trainBookings.get(trainNo).addBookedAndAvailableSeatsByDateStore(dateOfJourney, seatAllocationHelper, seatClass);
		}
    	
    }
    private BookedAndAvailableSeatsByDate getSeatStore(Train train, LocalDate dateOfJourney, int seatClass) {
    	  int trainNo = train.getId();
    	  if (seatClass == 1) {
    	    return trainBookings.get(trainNo).getSleeperSeating(dateOfJourney);
    	  } else {
    	    return trainBookings.get(trainNo).getACSeating(dateOfJourney);
    	  }
    	}
	public int getSeatForTrainAndDate(Train train, LocalDate dateOfJourney, int seatClass, SeatType seatType, int sourceIndex, int destinationIndex  ,int trainDestinationIndex) {
		int trainNo = train.getId();
		int seatNo = -1;
		ensureSeatStoreExists(train,dateOfJourney,seatClass);
		
		seatNo = trainBookings.get(trainNo).getBookedAndAvailableSeatsByDateStore(dateOfJourney, seatClass).findAppropriateSeat(seatType, sourceIndex, destinationIndex, trainDestinationIndex);	
		return seatNo;
	}
	
	
	
    
	/*
	 * public static void main(String[] args) { BookingDB b = new BookingDB();
	 * System.out.println(b.generateId());
	 * 
	 * }
	 */
}
