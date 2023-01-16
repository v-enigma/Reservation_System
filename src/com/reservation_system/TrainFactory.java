package com.reservation_system;

import java.util.ArrayList;
import java.util.List;

import com.database.TrainsData;
import com.enums.SeatType;


public class TrainFactory {
	private final static TrainFactory TRAIN_FACTORY = new TrainFactory();
	private static int helperId =1000;
	private TrainFactory() {
		
	}
	public static TrainFactory getInstance() {
		return TRAIN_FACTORY;
	}
	
	
	private Route buildRoute(List<Station>stations, List<Integer> allDistances) {
		Route route = new Route();
		route.addStationWithDistance(stations, allDistances);
		return route;
	}
	private List<Seat> createSeats(int seatsPerCoach){ //needs update on seat type assignment.contradicting the seat type in seat allocation
		List<Seat> seats = new ArrayList<>();
		for(int i=1;i<=seatsPerCoach;i++) {
			SeatType sType =null;
			if(i%8 ==0)
				sType = SeatType.SUB;
		
			else if(i%8 ==1||i%8 == 4)
				sType = SeatType.LB;
			else if(i%8 == 2|| i%8 == 5)
				sType = SeatType.MB;
			else if(i%8 ==3|| i%8 ==6)
				sType = SeatType.UB;
			else
				sType = SeatType.SLB;
			Seat seat = new Seat(i,sType);
			seats.add(seat);
		}
		return seats;
	}
	private Seating buildCoaches(int coachCount, String name, int seatsPerCoach) {
		Seating seating = null;
		if(name.equals("S"))
			seating = new SleeperSeating();
		else
			seating = new ACSeating();
		for(int i = 1;i <= coachCount;i++) {
			String tempName = name+i;
			List<Seat> seats = createSeats(seatsPerCoach);
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
	public List<Integer> getTrainsRegIds(int trainNo){
		return TrainsData.getInstance().getAllTrainRegIdsMappedToTrainNo(trainNo);
	}
	public Train getTrain(int trainRegId){
		return TrainsData.getInstance().getTrainByRegId(trainRegId);

	}
	  

}
