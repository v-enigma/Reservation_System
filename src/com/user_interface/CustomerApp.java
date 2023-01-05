package com.user_interface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.database.AuthenticationData;
import com.database.StationsData;
import com.database.UsersData;
import com.enums.Gender;
import com.enums.SeatType;
import com.exceptions.AuthenticationException;
import com.reservation_system.BookingFactory;
import com.reservation_system.Station;
import com.reservation_system.Train;
import com.reservation_system.UserDetails;
import com.reservation_system.UserFactory;

public class CustomerApp implements Application, Authenticable, Searchable{
	String userId= null;
	public void init()  {
		//update the code 
		boolean run = true;
		while (run) {
			System.out.println(" Enter your code\n 1. To SignIn\n 2.SignUp\n 3.Exit\n ");
			int option = Helper.getIntegerInput(); 
			switch (option) 
			{
			case 1: 
				signIn();
				break;
			case 2:
				signUp();
				break;
			case 3:
				run = false;
				break;
			}
			
		}
	}
	public void signIn()  {
		userId = "Venu1297"; // update the code
		String password = "QWzx0945@";// update the code;
		try {
		boolean success =AuthenticationData.getInstance().customerAuthenticate(userId, password);
		
			if(success) {
				System.out.println("Welcome "+ userId);
				menu();
			}
			else {
				throw new AuthenticationException();
			}
		}catch(AuthenticationException e) {
			System.out.println(PrintStatements.LOGIN_FAILURE);
			signIn();
		}
		
		
	}
	 void signUp() {
		System.out.println("Enter userId");
		String userId = "Venu1297";
		while(AuthenticationData.getInstance().isUserIdExists(userId)) {
			System.out.println("UserId is not Available. Please try another userId ");
			userId = Helper.getStringInput();
			
		}
		String password = "QWzx0945@";//Helper.getStringInput();//needs update. Have to implement regex to match 8 characters pattern
		String name = "Venugopal";//Helper.getStringInput();;
		String mail = "venu9821@gmail.com ";// Helper.getStringInput();
		String phone = "8179639025";//Helper.getStringInput();
		String dateOfBirth = "1998-04-12"; //Helper.get
		Gender gender = Gender.M; 
		//System.out.println("Do you have any seat preference?");
		SeatType seatPreference = SeatType.LB;
		boolean add =UserFactory.getInstance().createUser(userId,phone, mail, name, LocalDate.parse(dateOfBirth), gender, seatPreference,password );
		if(add) {
			System.out.println("Created Successfully.");
			signIn();
		}
		
		
	}
	private void bookTicket() {
		System.out.println("Enter source, destination station  and  date of journey\n ");
		List<Object> objects = getJourneyDetails();
		LocalDate journeyDate = (LocalDate)objects.get(2);
		LocalDate upperBoundDate = LocalDate.now().plusDays(120);
		Station jSource = StationsData.getInstance().findStation(objects.get(0).toString()) ;// using station code 
		Station jDestination = StationsData.getInstance().findStation(objects.get(1).toString());  // using station code
		while(!(journeyDate.isBefore(upperBoundDate) || journeyDate.isEqual(upperBoundDate))) {
			System.out.println("Enter a valid Date. You are allowed to book only 4 months ahead from today");
			String date = Helper.getStringInput();
			journeyDate  = LocalDate.parse(date);
		}
		List<Train> trains =searchTrain(objects);
		System.out.println("Enter the index of the train printed above to find available seats ");
		int index = Helper.getIntegerInput();
		index--;
		Train train = trains.get(index);	
		/*
		 * List<String>stops = train.getSchedule().getStops(); int indexOfSource =
		 * stops.indexOf(sourceId); int indexOfDestin = stops.indexOf(desId);
		 */
		System.out.println("Enter booking class.\n 1.Sleeper 2.AC  ");
	    int seatClass = Helper.getIntegerInput();
		List<UserDetails>passengers = getPassengerDetails();
	    BookingFactory.getInstance().createBooking(UsersData.getInstance().getUser(userId), passengers,train, journeyDate ,jSource, jDestination,seatClass);
	    
		
	}
	private List<UserDetails> getPassengerDetails(){
		System.out.println("Enter passengers count");
		int passengerCount = Helper.getIntegerInput();
	    List<UserDetails> passengers = new ArrayList<>();
	    while(passengerCount> 0) {
	    	System.out.println("Enter passengerName");
	    	String name = Helper.getStringInput();
	    	System.out.println("Enter DateofBirth in DD/MM/YYYY");
	    	String date = Helper.getStringInput();
	    	LocalDate dateOfBirth = LocalDate.parse(date);
	    	System.out.println("Enter Gender");
	    	char input = Helper.getCharacterInput();
	    	Gender gender ;
	    	if(input == 'M')
	    		gender = Gender.M;
	    	else if(input == 'F')
	    		gender = Gender.F;
	    	else 
	    		gender = Gender.O;
	    	System.out.println("Enter seat Preference");
	    	String seatInput = Helper.getStringInput();
	    	SeatType seatType ;
	    	if(seatInput == "LB")
	    		seatType = SeatType.LB;
	    	else if(seatInput == "MB")
	    		seatType = SeatType.MB;
	    	else if(seatInput == "UB")
	    		seatType = SeatType.UB;
	    	else if(seatInput == "SUB")
	    		seatType = SeatType.SUB;
	    	else
	    		seatType = SeatType.SLB;
	    	passengers.add(   UserFactory.getInstance().buildUserDetails(name, dateOfBirth, gender, seatType));
	    	passengerCount--;
	    }
	    return passengers;
	}
	private void cancel(long PNR) {
		
		BookingFactory.getInstance().cancelBooking(PNR, userId);
	}
	
	private void pastBooking() {
		
		BookingFactory.getInstance().getBookings(userId);
	}
	
	private  void menu() {
		System.out.println("Enter your option ");
		System.out.println("1. Search Train\n 2.BookTicket \n 3.Cancel \n 4.Past Bookings \n 5.PNR Status\n ");
		int option =1;
		switch(option){
		case 1:
			search();
			break;
		case 2:
			bookTicket();
			break;
		case 3:
			long PNR = Helper.getLongInput();
			cancel(PNR);
			break;
		case 4:
			pastBooking();
			break;
		case 5:
			
			pnrSearch();
			break;
		case 6:
			updateProfile();
		}
	}
	private void updateProfile() {
		
		System.out.println("Enter the option for which you want to update the details.\n1.to update name.\n2.To update email.3.To update phonenumber.\n");
	}

	
}

