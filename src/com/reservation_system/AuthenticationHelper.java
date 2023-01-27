package com.reservation_system;

import com.database.AuthenticationData;
import com.database.UsersData;

public class AuthenticationHelper {
   static  final private  AuthenticationHelper AUTHENTICATION_HELPER = new AuthenticationHelper();

    private AuthenticationHelper(){

    }
    public static AuthenticationHelper getInstance(){
        return AUTHENTICATION_HELPER;
    }
    //
    public boolean validateCustomer(String userId, String password){

        return UsersData.getInstance().validateCustomer(userId,password);
    }
    public boolean validateAdmin(String userId, String password){
        return UsersData.getInstance().validateAdmin(userId,password);
    }
}
