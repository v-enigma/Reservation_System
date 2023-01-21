package com.database;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.reservation_system.User;

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
