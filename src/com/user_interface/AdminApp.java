package com.user_interface;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.database.AuthenticationData;
import com.database.StationsData;
import com.enums.StationType;
import com.reservation_system.BookingFactory;
import com.reservation_system.ScheduleHelper;
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
		boolean run = true;
		while(run) {
			System.out.println("1.Add Train\n2.Schedule Train\n3.Remove Train\n4. Print Chart.\n5. Exit\n");
			System.out.println("Enter your option ");
			int option = Helper.getIntegerInput(); // has to be updated
			switch (option) {
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
					printChart();
					break;
				case 5:
					run = false;
					break;
			}
		}
	}
	private void printChart() {
		System.out.println("Enter the train No");
		int trainNo = Helper.getIntegerInput();
		if(TrainFactory.getInstance().validateTrain(trainNo)){
			LocalDate dateOfJourney;
			do {
				System.out.println("Enter date of journey in YYYY-MM-DD");
				String dateInStringForm = Helper.getStringInput();

				 dateOfJourney = LocalDate.parse(dateInStringForm);

			}while(dateOfJourney.isBefore(LocalDate.now()));

			BookingFactory.getInstance().printTrainBookingsFromTrain(trainNo, dateOfJourney);
		}
		
	}


	private void removeTrain() {
		
		
	}
	private DayOfWeek getDay(String day){
		DayOfWeek dayOfWeek = null;
		switch(day){
			case "MONDAY":
				dayOfWeek = DayOfWeek.MONDAY;
				break;
			case "TUESDAY" :
				dayOfWeek = DayOfWeek.TUESDAY;
				break;
			case "WEDNESDAY"	:
				dayOfWeek =  DayOfWeek.WEDNESDAY;
				break;
			case "THURSDAY":
				dayOfWeek =  DayOfWeek.THURSDAY;
				break;
			case "FRIDAY":
				dayOfWeek =  DayOfWeek.FRIDAY;

				break;
			case "SATURDAY" :
				dayOfWeek = DayOfWeek.SATURDAY;
				break;
			case "SUNDAY" :
				dayOfWeek = DayOfWeek.SUNDAY;
				break;
			default:
				System.out.println("Please Enter valid day of the week.");
				break;


		}
		return dayOfWeek;
	}
	private DayOfWeek InputValidDay(){
		System.out.println("Enter Name of the day in week period");
		String day = Helper.getStringInput();
		day = day.toUpperCase();
		DayOfWeek dayOfWeek = getDay(day);
		if( dayOfWeek == null){
			return InputValidDay();
		}
		else{
			return dayOfWeek;
		}
	}
	private List<DayOfWeek> setScheduledDaysInWeek(int noOfDays){
		List<DayOfWeek> scheduledDaysInWeek = new ArrayList<>();
		System.out.println(PrintStatements.WEEK_DAY);
		//int noOfDays = Helper.getIntegerInput();
		while(noOfDays > 0){
			DayOfWeek dayofWeek = InputValidDay();
			scheduledDaysInWeek.add(dayofWeek);
			noOfDays--;
		}
		return scheduledDaysInWeek;
	}
	private String validateTimeFormat(String time){
		String regex = "[0-9]{2}:[0-9]{2}";
		do{
			System.out.println(PrintStatements.TIME_FORMAT);
			time = Helper.getStringInput();
		}while(!Pattern.matches(regex,time));
		return time;
	}
	private void setStopInJourney(List<Boolean> isStopList, String code){
		System.out.println("Is "+ code+ "  a stop ? . Enter Yes or No");
		String isStop = Helper.getYesOrNo();

		if(isStop.equalsIgnoreCase("Yes")) {
			isStopList.add(true);
		}
		else
			isStopList.add(false);
	}
	private void setArrivalTimeForAllStops(String code, List<LocalTime> arrivalTimeList){
		System.out.println("Enter arrival Time (HH,MM) for " + code);
		String time = "0";
		time = validateTimeFormat(time);
		LocalTime arrivalTime = LocalTime.parse(time);
		arrivalTimeList.add(arrivalTime);
	}
	private void scheduleTrain() {
		System.out.println(PrintStatements.SCHEDULE_TRAIN);
		int trainNo = Helper.getIntegerInput();

		List<Integer> trainRegIds = TrainFactory.getInstance().getTrainsRegIds(trainNo);
		if(trainRegIds == null){
			System.out.println(PrintStatements.TRAIN_NUMBER_ERROR);
			return;
		}
		System.out.println(PrintStatements.TRAINS_AVAILABLE);
		for(Integer trainRegId: trainRegIds){
			System.out.println(trainRegId);
		}

		System.out.println(PrintStatements.REG_ID);
		int regId = Helper.getIntegerInput(); // validation missing
		while(!trainRegIds.contains(regId)){
			System.out.println(PrintStatements.DATE_OF_BIRTH);
			regId = Helper.getIntegerInput();
		}
		List<LocalTime> arrivalTimeList = new ArrayList<>();
		arrivalTimeList.add(LocalTime.parse("00:00"));
		System.out.println(PrintStatements.FREQUENCY);
		int noOfDaysScheduled = Helper.getIntegerInput();
		List<String> stationCodes = TrainFactory.getInstance().getTrain(regId).getRoute().getStationCodesInRoute();
		//System.out.println(stationCodes);
		boolean isFirstIteration = true;
		List<Boolean> isStopList = new ArrayList<>();
		List<List<DayOfWeek>> allStopsScheduledDays = new ArrayList<>();
		LocalTime sourceDepartureTime = LocalTime.parse("00:00");
		for(String code : stationCodes){
			if(isFirstIteration){
				System.out.println("Enter the departure time of " + code+".");
				String time= "0";
				time = validateTimeFormat(time);
				sourceDepartureTime = LocalTime.parse(time);
				isFirstIteration = false;
			}
			else {
				setArrivalTimeForAllStops(code, arrivalTimeList);
			}
			if(!(code.equals(stationCodes.get(0) )|| code.equals(stationCodes.get(stationCodes.size()-1))))
				setStopInJourney(isStopList,code);
			else{
				isStopList.add(true);
			}
			List<DayOfWeek> daysOfWeek = setScheduledDaysInWeek(noOfDaysScheduled);
			allStopsScheduledDays.add(daysOfWeek);

		}
		ScheduleHelper.getInstance().createSchedule(regId, trainNo,arrivalTimeList,sourceDepartureTime,isStopList,allStopsScheduledDays);

	}

	private Station setDetailsForStation(String name) {
		System.out.println("Enter station code");
		String sCode = Helper.getStringInput();
		System.out.println("Enter no of stations in the platform");
		int noOfPlatforms = Helper.getIntegerInput();
		System.out.println(PrintStatements.STATION_TYPE);
		char stationType = Helper.getCharacterInput();
		StationType sType = null;
		if(stationType == 'J')
			sType = StationType.JUNCTION;
		else if(stationType == 'T')
			sType = StationType.TERMINUS;
		else if(stationType == 'C')
			sType = StationType.CENTRAL;
		else 
			sType = StationType.STATION;
		return StationsData.getInstance().addStation(name, sCode, noOfPlatforms, sType);
	}
	private Station validateStationExistence(String stationName) {
		Station station = StationsData.getInstance().findStation(stationName);
		while(station == null) {
			System.out.println(PrintStatements.STATION_NOT_FOUND);
			char input = Helper.getCharacterInput();
			if(input == 'Y'|| input =='y') {
				station = setDetailsForStation(stationName);
			}
			else {
				System.out.println(PrintStatements.VALID_STATION);
				stationName = Helper.getStringInput();
			}
		}
		return station;
	}
	
	private void addTrain() {
		boolean hasAdded = false;
		System.out.println(PrintStatements.TRAIN_NUMBER);
		int trainId = Helper.getIntegerInput();
		System.out.println(PrintStatements.TRAIN_NAME);
		String trainName = Helper.getStringInput();
		System.out.println(PrintStatements.TRAIN_SOURCE);

		String source = Helper.getStringInput().toUpperCase();

		Station sStation = validateStationExistence(source);
		System.out.println(PrintStatements.TRAIN_DESTINATION);
		String destination = Helper.getStringInput().toUpperCase();
		Station desStation = validateStationExistence(destination);
		boolean hasRoute = TrainFactory.getInstance().ensureRouteExistence(source, destination);

		if(hasRoute) {

			System.out.println(PrintStatements.AC_COACH_COUNT);
			int acCoachCount = Helper.getIntegerInput();
			System.out.println(PrintStatements.SLEEPER_COACH_COUNT);
			int sleeperCount = Helper.getIntegerInput();
			hasAdded = TrainFactory.getInstance().createTrain(trainId, trainName, acCoachCount, sStation, desStation, sleeperCount, 64, 72);
			if (hasAdded) {
				System.out.println("Added Successfully");
			} else {
				System.out.println("Failed to add the train.");
			}
		}
		else{
			System.out.println("There is no route between the  source "+ source +"  and destination "+ destination );
		}
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
