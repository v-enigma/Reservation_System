package com.UserInterface;

import java.time.LocalDate;
import java.util.List;

import com.Database.AuthenticationData;
import com.Reservation_System.Train;
import com.Reservation_System.UserFactory;
import com.Reservation_System.Enum.Gender;
import com.Reservation_System.Enum.SeatType;

public class CustomerApp implements Application, Authenticable, Searchable{
	
	public void init() {
		System.out.println("  ");
		int option = 1; //update the code 
		while (true) {
			switch (option) 
			{
			case 1: 
				signIn();
				break;
			case 2:
				signUp();
				break;
			}
		}
	}
	public void signIn() {
		String userId = "Venu1297"; // update the code
		String password = "QWzx0945@";// update the code;
		
		boolean success =AuthenticationData.getInstance().customerAuthenticate(userId, password);
		
		if(success) {
			System.out.println("Welcome "+ userId);
			menu();
		}
		
	}
	public void signUp() {
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
		String dateOfBirth = "12-04-1998"; //Helper.get
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
		List<Object> objects = getDetails();
		List<Train> trains =searchTrain(objects);
		System.out.println("Enter the index of the train printed above to find available seats ");
		int index = Helper.getIntegerInput();
		index--;
		Train train = trains.get(index);
		
		
	}
	private void cancel(long PNR) {
		
	}
	private void search() {
		System.out.println("Enter source, destination station  and  date of journey\n ");
		List<Object> objects = getDetails();
		List<Train> trains =searchTrain(objects);
		System.out.println("If you are intersted in booking the ticket enter Y for yes or N for NO");
		char interested = Helper.getCharacterInput();
		if(interested == 'Y' || interested =='y') {
			
			System.out.println("Enter the index of the trains printed above");
			int index = Helper.getIntegerInput();
			index = index-1;
			
			
			//call booking method
		}
	}
	private void pastBooking() {
		
		
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
			long PNR = 0;
			cancel(PNR);
			break;
		case 4:
			pastBooking();
			break;
		case 5:
			pnrSearch();
			break;
		}
	}

	
}

