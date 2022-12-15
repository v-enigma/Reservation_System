package com.Reservation_System;

import com.Reservation_System.Enum.SType;

public class Station {
	private final String name;
	private final String id;
	private int noOFPlatforms;
	private SType stype;
	
	Station(String name, String id, int noOfPlatforms, SType stype){
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

	public SType getStype() {
		return stype;
	}

	public void setNoOFPlatforms(int noOFPlatforms) {
		this.noOFPlatforms = noOFPlatforms;
	}

	public void setStype(SType stype) {
		this.stype = stype;
	}
	

}
