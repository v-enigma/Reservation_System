package com.database;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.enums.StationType;
import com.reservation_system.TrainTimingAtStation;
import com.reservation_system.Schedule;
import com.reservation_system.Station;



public class ScheduleData {
	private List<Schedule> schedules = new ArrayList<>();
	
	private static ScheduleData SCHEDULE_TB = new ScheduleData();
	private ScheduleData() {

	}
	public void addSchedule(Schedule schedule){
		schedules.add(schedule);
	}
	public static ScheduleData getInstance() {
		return SCHEDULE_TB;
	}
	public  List<Integer> findTrainsBetweenStations(Station source, Station destination, LocalDate date) {
		DayOfWeek day = date.getDayOfWeek();
		System.out.println(day);
		List<Integer> trainNumbers = new ArrayList<>();
		String sCode = source.getId();
		String dCode = destination.getId();
		
		for(Schedule schedule :schedules) {

			Map<String, TrainTimingAtStation > stations = schedule.getStationsWithArrivalTime();


			if(stations.containsKey(sCode) && stations.containsKey(dCode) && stations.get(dCode).isStop() &&
					stations.get(sCode).isStop()&& stations.get(sCode).getscheduleDays().contains(day) ) {
				int sIndex = -1;
				int dIndex = -2;
				int index = 0;
				for(Map.Entry<String, TrainTimingAtStation> entry : stations.entrySet()){
					if(dCode.equalsIgnoreCase(entry.getKey()))
						dIndex = index;
					if(sCode.equalsIgnoreCase(entry.getKey()))
						sIndex = index;
					index++;
				}
				//System.out.println(sIndex +"  "+ dIndex);
				if(sIndex > dIndex)
					continue;
				trainNumbers.add(schedule.getTrainId());
				//System.out.println(trainNumbers);
				
			}
		}
		return trainNumbers;
		
	}
	/*public static void main(String[] args){
		Station source = new Station("A","A",2, StationType.CENTRAL);
		Station destination = new Station("E","E", 3, StationType.STATION);


	}*/
	

}
