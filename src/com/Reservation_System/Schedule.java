package com.Reservation_System;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
	//Scanner scan = new Scanner(System.in);
	private final Map<Station, LocalDateTime> stopsWithArrivalTime = new LinkedHashMap<>();
	public Schedule(Route route, List<LocalDateTime> arrivalTime){
		Iterator<Station> stops =route.getAllStops();
		int i=0;
		
		if(route.getStopsCount() == arrivalTime.size()) //
			
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
		System.out.println(dt);
		if(minutes>=60) {
		
			int hours = minutes/60;
			int leftOverMinutes = minutes%60;
			
			dt=dt.plusHours(hours);
			
			if(leftOverMinutes> 0)
				dt=dt.plusMinutes(leftOverMinutes);
				
		}
		else {
			dt =dt.plusMinutes(minutes);
			
		}
		stopsWithArrivalTime.put(station, dt);
		System.out.println(station.getId());
		System.out.println(dt);
	}
	
	public Map<Station, LocalDateTime> getStopswithArrivalTime(){
		return this.stopsWithArrivalTime;
	}
	
}
