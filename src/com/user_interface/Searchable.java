package com.user_interface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.database.BookingsData;
import com.database.ScheduleData;
import com.database.StationsData;
import com.database.TrainsData;
import com.reservation_system.Booking;
import com.reservation_system.Station;
import com.reservation_system.Train;

public interface Searchable {
	default void  pnrSearch() {
		Long pnr = Helper.getLongInput();
		Booking booking = BookingsData.getInstance().findBooking(pnr);
		if(booking == null) 
			System.out.println(PrintStatements.PNR_ERROR);
		
		else {
			System.out.println(booking);
		}
	}
	default List<Object> getJourneyDetails() {
		System.out.println("Enter the source station name");
		String source = Helper.getStringInput();
		System.out.println("Enter the destination station name");
		String destination= Helper.getStringInput();
		System.out.println("Enter date of journey.Enter the date in YYYY-MM-DD format");
		String date = Helper.getStringInput();
		LocalDate dateOfJourney = LocalDate.parse(date);
		List<Object> objects = new ArrayList<>();
		objects.add(source);
		objects.add(destination);
		objects.add(dateOfJourney);
		return objects;
	}
	default  void search() {
		System.out.println("Enter source, destination station  and  date of journey\n ");
		List<Object> objects = getJourneyDetails();
		searchTrain(objects);
		/*
		 * System.out.
		 * println("If you are intersted in booking the ticket enter Y for yes or N for NO"
		 * );
		 * 
		 * char interested = Helper.getCharacterInput(); if(interested == 'Y' ||
		 * interested =='y') {
		 * 
		 * System.out.println("Enter the index of the trains printed above"); int index
		 * = Helper.getIntegerInput(); index = index-1;
		 * 
		 * 
		 * //call booking method }
		 */		 
	}
	default List<Train>searchTrain(List< Object> objects ){
		//System.out.println("I entered into");
		String source= objects.get(0).toString();
		String destination =objects.get(1).toString();
		
		Station sStation =StationsData.getInstance().findStation(destination);
		Station dStation=StationsData.getInstance().findStation(source);
		//System.out.println("Enter date of journey.Enter the date in DD/MM/YYYY format");
		//String date = "";
		LocalDate dateOfJourney = (LocalDate)objects.get(2);
		List<Train> trains = new ArrayList<>();
		//System.out.println("I entered into");
		if(sStation == null || dStation == null)
			if(sStation == null)
				System.out.println("Invalid source station");
			else
				System.out.println("Invalid destination station");
		
		else {	
		
			List<Integer> trainNumbers = ScheduleData.getInstance().findtrainsBetweenStations(sStation, dStation,dateOfJourney);
			int count =0;
			for(int trainNumber: trainNumbers) {
				count++;
				Train train = TrainsData.getInstance().findTrainById(trainNumber);
				trainDetails(count, train);
				trains.add(train);
				
			}
			//System.out.println("I entered into");
		}
		return trains;
	}
	
	default void trainDetails(int index, Train train) {
		System.out.println(index+"  " +train.getId()+"  "+train.getName());
		
		
	}
	
}
