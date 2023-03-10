package com.reservation_system;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class Schedule {
	//Scanner scan = new Scanner(System.in);
	private final int id;
	private int trainId;
	
	private final Map<String, TrainTimingAtStation > stationsWithArrivalTime = new LinkedHashMap<>(); // station Code mapping to Arrival Time
	
	
	public Schedule(int id,int trainId ,Route route, List<LocalTime> arrivalTime, List<LocalTime> departureTime, List<Boolean> isStop, List<List<DayOfWeek>> scheduledDays ){
		this.setTrainId(trainId);
		this.id = id;
		Iterator<Station> stationsIterator = route.getAllStations();
		//Iterator<Boolean> haltIterator = isStop.iterator();
		
		int i=0;
		
		if(route.getStopsCount() == arrivalTime.size() &&  isStop.size() == arrivalTime.size()) //
			
			while(stationsIterator.hasNext() ) {
				Station station = stationsIterator.next();

				TrainTimingAtStation arrivalTimeAtStation = new TrainTimingAtStation(arrivalTime.get(i),departureTime.get(i), isStop.get(i), scheduledDays.get(i) );
				stationsWithArrivalTime.put(station.getId(), arrivalTimeAtStation );
				i++;
			}
		
	}
	public int  getId() {
		return id;
	}
	public void addDelay(Station station,int minutes) {
		updateDelay(station, minutes);
	}
	
	private void updateDelay(Station station ,int minutes) {
		String key = station.getId();
		TrainTimingAtStation scheduleAtStation = stationsWithArrivalTime.get(key);
		LocalTime lt = scheduleAtStation.getArrivalTime();
		System.out.println(lt);
		if(minutes>=60) {
		
			int hours = minutes/60;
			int leftOverMinutes = minutes%60;
			
			lt=lt.plusHours(hours);
			
			if(leftOverMinutes> 0)
				lt=lt.plusMinutes(leftOverMinutes);
				
		}
		else {
		lt =lt.plusMinutes(minutes);
			
		}
		scheduleAtStation.setArrivalTime(lt);
		//System.out.println(station.getId());
		//System.out.println(lt);
	}
	
	public Map<String, TrainTimingAtStation> getStationsWithArrivalTime(){
		return this.stationsWithArrivalTime;
	}
	public int getTrainId() {
		return trainId;
	}
	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}
	public List<String> getStopsCodes(){
		List<String> stops = new ArrayList<>();
		Iterator<Map.Entry<String,TrainTimingAtStation >>stationIterator = stationsWithArrivalTime.entrySet().iterator();
		while(stationIterator.hasNext()) {
			Map.Entry<String, TrainTimingAtStation> entry = stationIterator.next();
			if(entry.getValue().isStop()) {
				stops.add(entry.getKey());
			}
		}
		return stops;
	}


}
