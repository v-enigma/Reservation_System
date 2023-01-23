package com.reservation_system;

import java.time.LocalDate;
import java.util.List;

import com.database.AuthenticationData;
import com.database.UsersData;
import com.enums.Gender;
import com.enums.SeatType;



public class UserFactory {
	private final static UserFactory USER_FACTORY = new UserFactory() ;
	private UserFactory() {

		createUser("Venu1297","8179639025","venu8821@gmail.com", "Venugopal", LocalDate.parse("1998-04-12") ,Gender.MALE, SeatType.LB,"QWzx0945@");


	}
	public static UserFactory getInstance() {
		return USER_FACTORY;
	}
	public UserDetails buildUserDetails(String name, LocalDate dateOfBirth, Gender gender, SeatType seatPreference) {
		UserDetails userDetails = new UserDetails( name, dateOfBirth , gender, seatPreference);
		return userDetails;
	}
    public boolean createUser(String id, String phoneNumber, String mailId,String name, LocalDate dateOfBirth, Gender gender, SeatType seatPreference, String password) {
    	UserDetails userDetails = buildUserDetails(name, dateOfBirth, gender, seatPreference);
    	User user = new Customer(id, phoneNumber,mailId,userDetails);
    	
    	boolean stage1 =AuthenticationData.getInstance().addUserCred(id, password);
    	boolean stage2 = UsersData.getInstance().addUser(user);
    	return stage1 && stage2;
    }


   public User getUser(String userId){
	   return UsersData.getInstance().getUser(userId);
   }
   public String getBookings(String userId){
	  List<Booking> bookings =  ((Customer)(UsersData.getInstance().getUser(userId))).getBookings();
	  StringBuilder allBookings  = new StringBuilder();
	  for(Booking booking : bookings){
		  allBookings.append(booking.toString());
	  }
	  String bookingsInString =allBookings.toString();
	  return bookingsInString;
   }

   public String getUserProfile(String userId) {
	   StringBuilder details = new StringBuilder();
	   Customer customer = ((Customer) UserFactory.getInstance().getUser(userId));
	   details.append((customer.getUserDetails().getName()).concat("\t"));
	   details.append((customer.getUserDetails().getAge()));
	   details.append("\t".concat(customer.getUserDetails().getGender().toString()).concat("\t"));
	   details.append((customer.getMailId()).concat("\t"));
	   details.append((customer.getPhoneNumber()).concat("\t"));
	   return details.toString();
   }

}
