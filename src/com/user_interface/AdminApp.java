package com.user_interface;
import java.util.ArrayList;
import java.util.List;

import com.database.AuthenticationData;
import com.database.StationsData;
import com.enums.StationType;
import com.reservation_system.Station;
import com.reservation_system.TrainFactory;
import com.reservation_system.User;

public class AdminApp implements Application, Authenticable{
	String adminId= null;
	public void init() {
		
		System.out.println("Enter signIn details");
		signIn();
		}
		
	
	public void signIn() {
		adminId = "Venu1297"; // update the code
		String password = "QWzx0945@";// update the code;
		boolean success =AuthenticationData.getInstance().adminAuthenticate(adminId, password);
		if(success) {
			System.out.println("Welcome "+ adminId);
			menu();
		}
			
	}
	private void menu() {
		System.out.println("1.Add Train\n 2.Schedule Train \n 3.Remove Train\n  4. Print Chart  \n");
		System.out.println("Enter your option ");
		int option = 1; // has to be updated
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
		
		
	}

	private Station getAllStationDetails(String name) {
		System.out.println("Enter station code");
		String sCode = Helper.getStringInput();
		System.out.println("Enter no of stations in the platform");
		int noOfPlatforms = Helper.getIntegerInput();
		System.out.println("Enter station type .\n J for Junction.\n T for Terminus.\n C for Central.\n  S for station. \n");
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
			System.out.println("There is no such station in my storage.\n Do you like to add new station? If yes press 'Y' else 'N'." );
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
		System.out.println("Enter the source and destination stations to add train ");
		String source = Helper.getStringInput();
		Station sStation =validateStationExistence(source);
		String intermediateStation="";
		List<Station> allStations = new ArrayList<Station>();
		List<Integer> distanceInKm = new ArrayList<>();
		allStations.add(sStation);
		distanceInKm.add(0);
		System.out.println("Enter number of intermediate stations including destination Station");
		int noOfStations = Helper.getIntegerInput();
		while(noOfStations>0) {
			System.out.println("Enter intermediate stations including destination Station");
			intermediateStation = Helper.getLineInput();
			validateStationExistence(intermediateStation);
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
