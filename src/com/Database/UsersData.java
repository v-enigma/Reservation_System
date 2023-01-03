package com.Database;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.Reservation_System.User;

public class UsersData {
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
	/*
	 * public static void main(String[] args) { System.out.println("Hello I ");
	 * UserDB.getInstance(); }
	 */
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
	
}
