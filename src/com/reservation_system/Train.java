package com.reservation_system;

public class Train {
	
	private int id;
	private final int regId; // stores the number of the object created using train Class
	private String name;
	private Route route;
	private Schedule schedule;
	
	private final Seating acSeating ;
	private final Seating sleeperSeating;
	
	Train(Seating acSeating,Seating sleeperSeating, int instance){
		this.acSeating = acSeating;
		this.sleeperSeating = sleeperSeating;
		this.regId = instance;
		this.id =0;
		this.name = "";
		this.schedule = null;
		this.route = null;
	}
	
	Train(int id,String name, Route route, Seating acSeating, Seating sleeperSeating, int instance){
		this.id =  id;
		this.name = name;
		this.route = route;
		//this.schedule = schedule;
		this.acSeating = acSeating;
		this.sleeperSeating = sleeperSeating;
		this.regId = instance;
	}
	
	public String getName() {
		return name;
	}
	
	
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule =schedule;
	}
	
	
	public Seating getAcSeating() {
		return acSeating;
	}
	public Seating getSleeperSeating() {
		return sleeperSeating;
	}
	
	public int getId() {
		return id;
	}
	public int getRegId() {
		return regId;
	}
	public Route getRoute() {
		return route;
	}
	
}
