package com.user_interface;
import java.util.ArrayList;
import java.util.List;

import com.database.AuthenticationData;
import com.database.StationsData;
import com.enums.StationType;
import com.reservation_system.Station;
import com.reservation_system.TrainFactory;

public class AdminApp implements Application, Authenticable{
	String adminId= null;
	public void init() {

		System.out.println("Enter signIn details");
		signIn();
		}
		
	
	public void signIn() {
		adminId = "Venu1297"; // update the code
		String password = "QWzx0945@";// update the code;
		boolean success = AuthenticationData.getInstance().adminAuthenticate(adminId, password);
		if(success) {
			System.out.println("Welcome "+ adminId);
			menu();
		}
		else{
			System.out.println("Login Failure");
		}
			
	}
	private void menu() {
		System.out.println("1.Add Train\n 2.Schedule Train \n 3.Remove Train\n  4. Print Chart  \n");
		System.out.println("Enter your option ");
		int option = Helper.getIntegerInput(); // has to be updated
		switch(option) {
		case 1:
			addTrain();
			break;
		case 2:
			scheduleTrain();
			break;
		case 3:
			removeTrain();
			break;
		case 4:
			getChart();
		}
	}
	private void getChart() {
		
		
	}


	private void removeTrain() {
		
		
	}


	private void scheduleTrain() {
		System.out.println("Enter the Train Number you want to schedule");
		int trainNo = Helper.getIntegerInput();
		System.out.println("Available trains for the given train Number");
		List<Integer> trainRegIds = TrainFactory.getInstance().getTrainsRegIds(trainNo);
		for(Integer trainRegId: trainRegIds){
			System.out.println(trainRegId);
		}
		System.out.println("Enter your train RegId you want to schedule");
		int regId = Helper.getIntegerInput();
		//TrainFactory.getInstance().getTrain(regId);


	}
	private void addArrivalTimeToStationsExceptSource(){

	}

	private Station getAllStationDetails(String name) {
		System.out.println("Enter station code");
		String sCode = Helper.getStringInput();
		System.out.println("Enter no of stations in the platform");
		int noOfPlatforms = Helper.getIntegerInput();
		System.out.println(PrintStatements.STATION_TYPE);
		char stationType = Helper.getCharacterInput();
		StationType stype = null;
		if(stationType == 'J')
			stype = StationType.JUNCTION;
		else if(stationType == 'T')
			stype = StationType.TERMINUS;
		else if(stationType == 'C')
			stype = StationType.CENTRAL;
		else 
			stype = StationType.STATION;
		return StationsData.getInstance().addStation(name, sCode, noOfPlatforms, stype);
	}
	private Station validateStationExistence(String stationName) {
		Station station = StationsData.getInstance().findStation(stationName);
		while(station== null) {
			System.out.println(PrintStatements.STATION_NOT_FOUND);
			char input = Helper.getCharacterInput();
			if(input == 'Y'|| input =='y') {
				station =getAllStationDetails(stationName);
			}
			else {
				System.out.println("Enter valid  station");
				stationName = Helper.getStringInput();
			}
		}
		return station;
	}
	
	private void addTrain() {
		System.out.println("Enter the train number");
		int trainId = Helper.getIntegerInput();
		System.out.println("Enter the name of the train");
		String trainName = Helper.getStringInput();
		System.out.println("Enter the source  of the train ");

		String source = Helper.getStringInput();

		Station sStation =validateStationExistence(source);
		String intermediateStation;
		List<Station> allStations = new ArrayList<>();
		List<Integer> distanceInKm = new ArrayList<>();
		allStations.add(sStation);
		distanceInKm.add(0);
		System.out.println("Enter number of intermediate stations including destination Station");
		int noOfStations = Helper.getIntegerInput();
		while(noOfStations>0) {
			System.out.println("Enter intermediate stations including destination Station");
			intermediateStation = Helper.getStringInput();

			validateStationExistence(intermediateStation);
			System.out.println(intermediateStation);
			System.out.println("Enter distance form source");
			int distance = Helper.getIntegerInput();
			allStations.add(sStation);
			distanceInKm.add(distance);
			noOfStations--;	
		}
		System.out.println("Enter no of AC coaches");
		int acCoachCount = Helper.getIntegerInput();
		System.out.println("Enter no of Sleeper coaches");
		int sleeperCount = Helper.getIntegerInput();
		TrainFactory.getInstance().createTrain(trainId,trainName, allStations, distanceInKm, acCoachCount, sleeperCount,64,72);
		
		/*
		Scanner scan = new Scanner(System.in);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy);
        LocalDate date = LocalDate.parse(userInput, dateFormat);
        System.out.println(date);
        LocalDateTime localDateTime2 = date.atTime(20,16);
        ArrayList<LocalDateTime> schedule = new ArrayList<>();
		*/ 
	}
	
}
