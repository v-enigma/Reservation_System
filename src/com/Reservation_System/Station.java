package com.Reservation_System;
import com.Reservation_System.Enum.StationType;

public class Station {
	private final String name;
	private final String id;
	private int noOFPlatforms;
	private StationType stype;
	
	public Station(String name, String id, int noOfPlatforms, StationType stype){
		this.name = name;
		this.id = id;
		this.noOFPlatforms = noOfPlatforms;
		this.stype = stype;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public int getNoOFPlatforms() {
		return noOFPlatforms;
	}

	public StationType getStype() {
		return stype;
	}

	public void setNoOFPlatforms(int noOFPlatforms) {
		this.noOFPlatforms = noOFPlatforms;
	}

	public void setStype(StationType stype) {
		this.stype = stype;
	}
	

}
