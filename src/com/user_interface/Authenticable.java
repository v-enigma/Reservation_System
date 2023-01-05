package com.user_interface;

import com.reservation_system.AuthenticationException;

public interface Authenticable {
	void signIn() throws AuthenticationException;
	//void signUp();
}
