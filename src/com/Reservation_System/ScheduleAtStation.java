package com.Reservation_System;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


public class ScheduleAtStation { // Schedule At Station stores information for a train for a station
	
	private LocalTime arrivalTime ;
	private boolean isStop;
	private  List<DayOfWeek> scheduleDays ;
	
	public ScheduleAtStation(LocalTime arrivalTime, boolean isStop, List<DayOfWeek> scheduleDays) {
		this.arrivalTime = arrivalTime;
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
	
	
}
