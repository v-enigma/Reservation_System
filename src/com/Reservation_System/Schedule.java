package com.Reservation_System;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Schedule {
	private final Map<Station, LocalDateTime> stopsWithArrivalTime = new HashMap<>();
	public Schedule(Route route, List<LocalDateTime> arrivalTime){
		Iterator<Station> stops =route.getAllStops();
		int i=0;
		if(stopsWithArrivalTime.size() == arrivalTime.size())
			while(stops.hasNext() ) {
				Station station = stops.next();
				stopsWithArrivalTime.put(station, arrivalTime.get(i));
				i++;
			}
	}
	public void addDelay(Station station,int minutes) {
		updateDelay(station, minutes);
	}
	private void updateDelay(Station station ,int minutes) {
		LocalDateTime dt = stopsWithArrivalTime.get(station);
		if(minutes>=60) {
			int hours = minutes/60;
			int leftOverMinutes = minutes%60;
			
			dt.plusHours(hours);
			if(leftOverMinutes> 0)
				dt.plusMinutes(leftOverMinutes);
			
		}
		else {
			dt.plusMinutes(minutes);
		}
		stopsWithArrivalTime.put(station, dt);
		
	}
	
	
	/*
	 * public static void main(String[] args) { Route route = new Route();
	 * route.ArrivalDateAndTime = LocalDateTime.now();
	 * System.out.println(route.ArrivalDateAndTime); }
	 */
	
	
}
