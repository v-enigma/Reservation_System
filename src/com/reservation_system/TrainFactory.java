package com.reservation_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.database.NetworkStorage;
import com.database.StationsData;
import com.database.TrainsData;
import com.enums.SeatType;
import sun.rmi.runtime.NewThreadAction;


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
	public boolean ensureRouteExistence(String sourceName, String destinationName){
		List<String>stations =NetworkStorage.getInstance().getAvailableRouteBetweenStations(sourceName, destinationName);
		if(stations!= null)
			return true;
		else
			return false;
	}
	private List<Integer> generateDistance(int count){
		Random rand = new Random();
		List<Integer> allDistances = new ArrayList<>();
		allDistances.add(0);
		for(int i=1;i<count;i++){
			int distance = rand.nextInt(100)+10;
			allDistances.add(distance);
		}
		return allDistances;
	}
	public boolean createTrain(int trainId,String name, int acCoachCount,Station source, Station destination, int sleeperCoachCount ,int acCoachSeatCount , int sleeperCoachSeatCount) {
	    Train train =null;
		boolean hasCreated = false;
	    int id = trainId;
		List<String> stationsNames = NetworkStorage.getInstance().getAvailableRouteBetweenStations(source.getName(), destination.getId());
		List<Integer> allDistances = generateDistance(stationsNames.size());
		System.out.println(stationsNames);
	    List<Station> stations = StationsData.getInstance().getStationListFromNameList(stationsNames);
		Route route = buildRoute(stations, allDistances);
	    Seating sleeperSeating = buildCoaches(sleeperCoachCount, "S",  sleeperCoachSeatCount);
	    Seating acSeating = buildCoaches(acCoachCount,"B", acCoachSeatCount);
	    train = new Train(id,name,route, acSeating, sleeperSeating,TrainsData.getHelperId());
	    hasCreated = TrainsData.getInstance().addTrain(train);
	    return true;
	}
	public List<Integer> getTrainsRegIds(int trainNo){
		return TrainsData.getInstance().getAllTrainRegIdsMappedToTrainNo(trainNo);
	}
	public Train getTrain(int trainRegId){
		return TrainsData.getInstance().getTrainByRegId(trainRegId);

	}
	public boolean validateTrain(int TrainNo){
		if(TrainsData.getInstance().findTrainById(TrainNo)!= null){
			return true;
		}
		else{
			return false;
		}

	}

}
