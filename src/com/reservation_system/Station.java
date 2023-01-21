package com.reservation_system;

import com.enums.StationType;

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


	public StationType getStype() {
		return stype;
	}




	

}
