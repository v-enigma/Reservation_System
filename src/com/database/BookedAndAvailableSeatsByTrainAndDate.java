package com.database;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reservation_system.BookingFactory;
import com.reservation_system.Train;
import com.enums.SeatType;



public class BookedAndAvailableSeatsByTrainAndDate {
	
	private final static BookedAndAvailableSeatsByTrainAndDate allocatedSeatsByTrain = new BookedAndAvailableSeatsByTrainAndDate();
	private final Map<Integer,MultiDayBookingStore > trainBookings = new HashMap<>(); //Stores the train number as the key and multiple days ahead booking as the value.
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
	public String getSeatForTrainAndDate(Train train, LocalDate dateOfJourney, int seatClass, SeatType seatType, String sourceCode, String destinationCode,  List<String> stopsCodes) {
		String stringSeatNo = null;
		int trainNo = train.getId();
		int seatNo = -1;
		ensureSeatStoreExists(train,dateOfJourney,seatClass);

		seatNo = trainBookings.get(trainNo).getBookedAndAvailableSeatsByDateStore(dateOfJourney, seatClass).findAppropriateSeat(seatType, sourceCode, destinationCode, stopsCodes);
		if(seatNo >0){

			return "C"+seatNo;
		}
		seatNo = trainBookings.get(trainNo).getBookedAndAvailableSeatsByDateStore(dateOfJourney, seatClass).findRACSeats();
			if(seatNo > 0){
				return "R"+seatNo;
			}

		seatNo = trainBookings.get(trainNo).getBookedAndAvailableSeatsByDateStore(dateOfJourney, seatClass).findWaitingListSeats();
		if (seatNo == -1) {
			stringSeatNo = "N" + seatNo;
		} else
			stringSeatNo = "W" + seatNo;


		return stringSeatNo;
	}
	
	public void freeSeat(Train train, LocalDate dateOfJourney, int seatClass, String sourceCode, String destinationCode,int seatNo){

		trainBookings.get(train.getId()).getBookedAndAvailableSeatsByDateStore(dateOfJourney, seatClass).freeSeat(sourceCode,destinationCode,seatNo);
	}
	
    
	/*
	 * public static void main(String[] args) { BookingDB b = new BookingDB();
	 * System.out.println(b.generateId());
	 * 
	 * }
	 */
}
