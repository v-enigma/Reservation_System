package com.Database;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationData {
	private final static AuthenticationData authenticationDB =new AuthenticationData();;
	Map<String , String> customerCred = new HashMap<>();
	Map<String, String>adminCred = new HashMap<>();
	
	private AuthenticationData() {
			
	}
	public static AuthenticationData getInstance() {
		
		return authenticationDB;
	}
	
	public boolean customerAuthenticate(String userId, String producedPassword ) {
		if(customerCred.containsKey(userId) ) {
			return customerCred.get(userId).equals(producedPassword);
		}
		return false;
		
	}
	public boolean adminAuthenticate(String userId, String producedPassword) {
		if(adminCred.containsKey(userId)) {
			return adminCred.get(userId).equals(producedPassword);
		}
		return false;
	}
	public boolean isUserIdExists(String userId) {
		return customerCred.containsKey(userId);
	}
	public boolean addUserCred(String userId, String password) {
		customerCred.put(userId, password);
		if(customerCred.containsKey(userId))
			return true;
		else
			return false;
		
	} 
}
