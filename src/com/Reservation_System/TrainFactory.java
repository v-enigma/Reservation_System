package com.Reservation_System;

import java.util.ArrayList;
import java.util.List;

import com.Database.TrainsData;
import com.Reservation_System.Enum.SeatType;

public class TrainFactory {
	private final static TrainFactory TRAIN_FACTORY = new TrainFactory();;
	private static int helperId =1000;
	private TrainFactory() {
		
	}
	public static TrainFactory getInstance() {
		return TRAIN_FACTORY;
	}
	private int getId() {
		
		return ++helperId;
	}
	
	private Route buildRoute(List<Station>stations, List<Integer> allDistances) {
		Route route = new Route();
		route.addStationWithDistance(stations, allDistances);
		return route;
	}
	private List<Seat> createSeats(int count){
		List<Seat> seats = new ArrayList<>();
		for(int i=1;i<=count;i++) {
			SeatType stype =null;
			if(i%8 ==0)
				stype = SeatType.SUB;
		
			else if(i%8 ==1||i%8 == 4)
				stype = SeatType.LB;
			else if(i%8 == 2|| i%8 == 5)
				stype = SeatType.MB;
			else if(i%8 ==3|| i%8 ==6)
				stype = SeatType.UB;
			else
				stype = SeatType.SLB;
			Seat seat = new Seat(i,stype);
			seats.add(seat);
		}
		return seats;
	}
	private Seating buildCoaches(int count, String name, int seatCount) {
		Seating seating = null;
		if(name =="S")
			seating = new SleeperSeating();
		else
			seating = new ACSeating();
		for(int i=1;i<=count;i++) {
			String tempName = name+i;
			List<Seat> seats = createSeats(seatCount);
			Coach coach = new Coach(i,seats,tempName);
			seating.addCoach(coach);
		}
		return seating;
	}
	
	public void createTrain(int trainId,String name,List<Station> stations,List<Integer> allDistances, int acCoachCount, int sleeperCoachCount ,int acCoachSeatCount , int sleeperCoachSeatCount) {
	    Train train =null;
	    int id = trainId;
	    Route route = buildRoute(stations, allDistances);
	    Seating sleeperSeating = buildCoaches(sleeperCoachCount, "S",  sleeperCoachSeatCount);
	    Seating acSeating = buildCoaches(acCoachCount,"B", acCoachSeatCount);
	    train = new Train(id,name,route, acSeating, sleeperSeating,TrainsData.getHelperId());//Think of the logic for the number and Id;
	    TrainsData.getInstance().addTrain(train);
	    
	}

	
	  

}
