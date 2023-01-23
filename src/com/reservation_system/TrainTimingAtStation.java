package com.reservation_system;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


public class TrainTimingAtStation { // Schedule At Station stores information for a train for a station
	
	private LocalTime arrivalTime ;
	private boolean isStop;
	private  List<DayOfWeek> scheduleDays ;
	private final LocalTime departureTime;
	public TrainTimingAtStation(LocalTime arrivalTime, LocalTime departureTime, boolean isStop, List<DayOfWeek> scheduleDays) {
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.isStop = isStop;
		this.scheduleDays = scheduleDays;
	}
	
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public boolean isStop() {
		return isStop;
	}
	
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	
	public List<DayOfWeek> getscheduleDays(){
		return scheduleDays;
	}
	
	public void setScheduleDays(List<DayOfWeek> scheduleDays) {
		this.scheduleDays = scheduleDays;
	}
	
}
