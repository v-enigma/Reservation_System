package com.database;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.reservation_system.User;

public final class UsersData {
	private final static UsersData USER_DB = new UsersData();
	private Set<User> users;
	private UsersData() {
		
		this.users = new HashSet<>();
	}

	public static UsersData getInstance() {

		return USER_DB;
	}
	public boolean addUser(User user) {
		return users.add(user);
	}

	public User getUser(String userId) {
		User user = null;
		Iterator<User> userIterator = users.iterator();
		while(userIterator.hasNext()) {
			User temp = userIterator.next();
			if(temp.getId().equals(userId))
				return temp;
		}
		return user;
	}
	public boolean validateCustomer(String userId, String password){
		return AuthenticationData.getInstance().customerAuthenticate(userId, password);
	}
	public boolean validateAdmin(String userId, String password){
		return AuthenticationData.getInstance().adminAuthenticate(userId, password);
	}
	
}
