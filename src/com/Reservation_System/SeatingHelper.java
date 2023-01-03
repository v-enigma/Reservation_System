package com.Reservation_System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SeatingHelper {
	
	private static SeatingHelper SEATING_HELPER = new SeatingHelper();
	private SeatingHelper() {
		
	}
	public static SeatingHelper getInstance() {
		return SEATING_HELPER;
	}
	
	private void addStationCodes(List<String> stationCodes, HashMap<String,ArrayList<Integer>> bookedSeats) {
		Iterator<String> sCodes = stationCodes.iterator();
		while(sCodes.hasNext()) {
			
			String Id = sCodes.next();
			bookedSeats.put(Id, new ArrayList<Integer>());
		}
	}
	
	public HashMap<String, ArrayList<Integer>> createBookedSeatsStorage(List<String> stationCodes)
	{
		 HashMap<String, ArrayList<Integer >> bookedSeats = new HashMap<>();
		 addStationCodes(stationCodes, bookedSeats);
		 return bookedSeats;
	}
	
	
	
}
