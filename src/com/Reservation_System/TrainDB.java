package com.Reservation_System;

public class TrainDB {
	private static int helperId =1000;
	private static TrainDB TRAIN_DB ;
	public static TrainDB getInstance() {
		if(TRAIN_DB == null) {
			TRAIN_DB = new TrainDB();
			
		}
		return TRAIN_DB;
	}
	private TrainDB() {
		
	}
	public int getId() {
		
		return ++helperId;
	}
}
