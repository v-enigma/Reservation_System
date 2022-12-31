package com.Reservation_System.Enum;

public enum StationType {
	JUNCTION(10),
	TERMINUS(3),
	CENTRAL(5),
	STATION(2);
	private int duration;
	StationType(int duration){
		this.duration = duration;
	}
	public int getDuration() {
		return duration;
	}
	
}
