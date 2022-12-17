package com.Reservation_System;

import com.Reservation_System.Enum.TrainType;

public class Train {
	
	private final int id;
	private final String name;
	private final Route route;
	private Schedule schedule;
	private TrainType type ;
	private final Seating acSeating ;
	private final Seating sleeperSeating;
	
	Train(String name,Route route,Schedule schedule, ACSeating acSeating, SleeperSeating sleeperSeating){
		this.id =  TrainDB.getInstance().getId();
		this.name = name;
		this.route = route;
		this.schedule = schedule;
		this.acSeating = acSeating;
		this.sleeperSeating = sleeperSeating;
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	public Route getRoute() {
		return route;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public TrainType getTrainType() {
		return type;
	}

	public Seating getAcSeating() {
		return acSeating;
	}
	public Seating getSleeperSeating() {
		return sleeperSeating;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule =schedule;
	}
	public void setTrainType(TrainType type) {
		this.type =type;
	}
}
