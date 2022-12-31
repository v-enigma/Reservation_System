package com.Reservation_System;

import java.time.LocalDate;

import com.Database.AuthenticationData;
import com.Database.UsersData;
import com.Reservation_System.Enum.Gender;
import com.Reservation_System.Enum.SeatType;


public class UserFactory {
	public final static UserFactory USER_FACTORY = new UserFactory() ;
	private UserFactory() {
		
	}
	public static UserFactory getInstance() {
		return USER_FACTORY;
	}
	private UserDetails buildUserDetails(String name, LocalDate dateOfBirth, Gender gender, SeatType seatPreference) {
		UserDetails userDetails = new UserDetails( name, dateOfBirth , gender, seatPreference);
		return userDetails;
	}
    public boolean createUser(String id, String phoneNumber, String mailId,String name, LocalDate dateOfBirth, Gender gender, SeatType seatPreference, String password) {
    	UserDetails userDetails = buildUserDetails(name, dateOfBirth, gender, seatPreference);
    	User user = new User(id, phoneNumber,mailId,userDetails);
    	
    	boolean stage1 =AuthenticationData.getInstance().addUserCred(id, password);
    	boolean stage2 = UsersData.getInstance().addUser(user);
    	return stage1 && stage2;
    }

}
