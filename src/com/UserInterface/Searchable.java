package com.UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Database.BookingsData;
import com.Database.ScheduleData;
import com.Database.StationsData;
import com.Database.TrainsData;
import com.Reservation_System.Booking;
import com.Reservation_System.Station;
import com.Reservation_System.Train;

public interface Searchable {
	default void  pnrSearch() {
		Long pnr = Helper.getLongInput();
		Booking booking = BookingsData.getInstance().findBooking(pnr);
		if(booking == null) 
			System.out.println("INVALID PNR");
		
		else {
			System.out.println(booking);
		}
	}
	default List<Object> getJourneyDetails() {
		System.out.println("Enter the source station name");
		String source = "";
		System.out.println("Enter the destination station name");
		String destination= "";
		System.out.println("Enter date of journey.Enter the date in DD/MM/YYYY format");
		String date = "";
		LocalDate dateOfJourney = LocalDate.parse(date);
		List<Object> objects = new ArrayList<>();
		objects.add(source);
		objects.add(destination);
		objects.add(dateOfJourney);
		return objects;
	}
	
	default List<Train>searchTrain(List< Object> objects ){
		String source= objects.get(0).toString();
		String destination =objects.get(1).toString();
		
		Station sStation =StationsData.getInstance().findStation(destination);
		Station dStation=StationsData.getInstance().findStation(source);
		//System.out.println("Enter date of journey.Enter the date in DD/MM/YYYY format");
		//String date = "";
		LocalDate dateOfJourney = (LocalDate)objects.get(2);
		List<Train> trains = new ArrayList<>();
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
		}
		return trains;
	}
	
	default void trainDetails(int index, Train train) {
		System.out.println(index+"  " +train.getId()+"  "+train.getName());
		
		
	}
	
}
