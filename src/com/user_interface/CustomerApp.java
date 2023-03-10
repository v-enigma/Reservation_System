package com.user_interface;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import com.database.AuthenticationData;
import com.database.StationsData;
import com.enums.Gender;
import com.enums.SeatType;
import com.exceptions.AuthenticationException;
import com.reservation_system.*;

public class CustomerApp implements Application, Authenticable, Searchable{
	String userId= null;
	public void init()  {
		//update the code 
		boolean run = true;
		while (run) {
			System.out.println(PrintStatements.OPTIONS);
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
		boolean success = AuthenticationHelper.getInstance().validateCustomer(userId, password);
		
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
		String userId = Helper.getLineInput();
		while(AuthenticationData.getInstance().isUserIdExists(userId)) {
			System.out.println(PrintStatements.USER_NOT_AVAILABLE);
			userId = Helper.getLineInput();
		}
		System.out.println("Please Enter password");
		System.out.println(PrintStatements.PASSWORD_RULES);
		String password = Helper.getPassword();
		String name = Helper.getLineInput();
		String mail = Helper.getEmailInput();
		String phone = Helper.getPhoneNumber();
		LocalDate dateOfBirth = Helper.getDateInput(); //Helper.get
		Gender gender = Helper.getGender();
		//System.out.println("Do you have any seat preference?");
		SeatType seatPreference = SeatType.LB;
		boolean add =UserFactory.getInstance().createUser(userId,phone, mail, name, dateOfBirth, gender, seatPreference,password );
		if(add) {
			System.out.println("Created Successfully.");
			signIn();
		}
	}
	private void bookTicket() {
		System.out.println(PrintStatements.JOURNEY_DETAILS);
		List<Object> objects = getJourneyDetails();
		LocalDate journeyDate = (LocalDate) objects.get(2);
		Station jSource = StationsData.getInstance().findStation(objects.get(0).toString());
		Station jDestination = StationsData.getInstance().findStation(objects.get(1).toString());
		/*LocalDate upperBoundDate = LocalDate.now().plusDays(120);

		while ((journeyDate.isAfter(upperBoundDate) || journeyDate.isEqual(upperBoundDate) || journeyDate.isBefore(LocalDate.now()))) {
			System.out.println(PrintStatements.DATE_VALIDATION);
			String date = Helper.getStringInput();
			journeyDate = LocalDate.parse(date);
		}*/
		List<Train> trains = searchTrain(objects);
		if (trains != null && trains.size() > 0) {
			System.out.println(PrintStatements.TRAIN_INDEX);
			int index = Helper.getIntegerInput();
			index--;
			Train train = trains.get(index);
			/*
			 * List<String>stops = train.getSchedule().getStops(); int indexOfSource =
			 * stops.indexOf(sourceId); int indexOfDestin = stops.indexOf(desId);
			 */
			System.out.println(PrintStatements.BOOKING_CLASS);
			int seatClass = Helper.getIntegerInput();
			List<UserDetails> passengers = getPassengerDetails();
			String allocatedSeatDetails  = BookingFactory.getInstance().createBooking(UserFactory.getInstance().getUser(userId), passengers, train, journeyDate, jSource, jDestination, seatClass);
			System.out.println(allocatedSeatDetails);
		}
	}
	private List<UserDetails> getPassengerDetails(){
		System.out.println(PrintStatements.PASSENGERS_COUNT);
		System.out.println("Enter passengers count");

		int passengerCount = Helper.getIntegerInputInARange(1,6);
	    List<UserDetails> passengers = new ArrayList<>();
	    while(passengerCount> 0) {
	    	System.out.println("Enter passengerName");
	    	String name = Helper.getLineInput();
			//System.out.println(name);
	    	System.out.println(PrintStatements.DATE_OF_BIRTH);
	    	LocalDate dateOfBirth = Helper.getDateInput();
			LocalDate today = LocalDate.now();
			Period fiveYears = Period.between(dateOfBirth, today);
			if(fiveYears.getYears() <= 5){
				System.out.println("For 5 years old or below you don't need a ticket");
				passengerCount--;
				continue;
			}

	    	//System.out.println("Enter Gender");
	    	Gender gender = Helper.getGender();
	    	SeatType seatType = Helper.getSeatType();
	    	passengers.add( UserFactory.getInstance().buildUserDetails(name, dateOfBirth, gender, seatType));
	    	passengerCount--;
	    }
	    return passengers;
	}
	private void cancel(long PNR) {
		
		BookingFactory.getInstance().cancelBooking(PNR, userId);
	}
	
	private void pastBooking() {

		String allBookings = UserFactory.getInstance().getBookings(userId);
		if(allBookings.isEmpty())
			System.out.println(PrintStatements.EMPTY_BOOKINGS);
		else
			System.out.println(allBookings);
	}
	
	private  void menu() {

		//System.out.println(PrintStatements.CUSTOMER_OPTIONS);
		boolean repeat = true;
		while(repeat){

			System.out.println(PrintStatements.CUSTOMER_OPTIONS);
			System.out.println(PrintStatements.GET_OPTION);
			int option = Helper.getIntegerInput();
			switch(option) {
				case 1:
					search();
					break;
				case 2:
					bookTicket();
					break;
				case 3:
					System.out.println(PrintStatements.CANCEL_PNR);
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
					break;
				case 7:
					displayProfile();
					break;
				case 8:
					repeat = false;
					break;

			}
		}
	}
	private void updateName(String name){

		UserFactory.getInstance().getUser(userId).getUserDetails().setName(name);
	}
	private void updateEmail(String email){

		UserFactory.getInstance().getUser(userId).setMailId(email);
	}
	private void updatePhone( String phone){

		 UserFactory.getInstance().getUser(userId).setPhoneNumber(phone);

	}
	private void updateProfile() {
		System.out.println(PrintStatements.DETAILS_UPDATE);
		int option = Helper.getIntegerInput();
		switch (option){
			case 1:
				String updatedName = Helper.getStringInput();
				updateName(updatedName);
				break;
			case 2:
				String updatedEmail = Helper.getStringInput();
				updateEmail(updatedEmail);
				break;
			case 3:
				String updatedPhone = Helper.getPhoneNumber();
				updatePhone(updatedPhone);
				break;
		}

	}
	private void displayProfile(){
		String details = UserFactory.getInstance().getUserProfile(userId);
		System.out.println(details);
	}

	
}

