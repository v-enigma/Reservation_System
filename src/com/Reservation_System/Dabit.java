package com.Reservation_System;

import java.time.LocalDate;
import java.util.HashMap;

public class Dabit { // multiple Days Ahead Booking Including Today storage
	private int trainId;
	private final  HashMap<LocalDate ,SeatAllocationHelper >advancedSleeperSeating = new HashMap<>();
	private final HashMap<LocalDate, SeatAllocationHelper> advancedACSeating= new HashMap<>();
	
	public SeatAllocationHelper getSleeperSeating(LocalDate date) {
		return advancedSleeperSeating.get(date);
	}
	public SeatAllocationHelper getACSeating(LocalDate date) {
		return advancedACSeating.get(date);
	}
	public int getTrainId() {
		return trainId;
		
	}
	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}
}
