package com.Database;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.Reservation_System.Schedule;
import com.Reservation_System.ScheduleAtStation;
import com.Reservation_System.Station;



public class ScheduleData {
	private List<Schedule> schedules = new ArrayList<>();
	
	public static ScheduleData SCHEDULE_TB = new ScheduleData();
	private ScheduleData() {
		
	}
	public static ScheduleData getInstance() {
		return SCHEDULE_TB;
	}
	public  List<Integer> findtrainsBetweenStations(Station source, Station destination, LocalDate date) {
		DayOfWeek day = date.getDayOfWeek();
		List<Integer> trainNumbers = new ArrayList<>();
		String sCode = source.getId();
		String dCode = destination.getId();
		
		for(Schedule schedule :schedules) {
			Map<String, ScheduleAtStation>stations = schedule.getStationswithArrivalTime();
			if(stations.containsKey(sCode) && stations.containsKey(dCode) && stations.get(dCode).isStop() && 
					stations.get(sCode).isStop()&& stations.get(sCode).getscheduleDays().contains(day)) {
				trainNumbers.add(schedule.getTrainId());
				
			}
		}
		return trainNumbers;
		
	}
	
	
	
}
