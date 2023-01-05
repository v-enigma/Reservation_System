package com.user_interface;
import java.util.Scanner;

public class Main{
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to reservation System");
		Application app = null;
		while(true) {
			System.out.println("Choose your option \n 1.Customer  \n 2.Admin \n 3.Guest");
			int option = scan.nextInt();
			System.out.println("Enter to ");
			switch(option) {
			case 1:
				(new CustomerApp()).init();;
				break;
			case 2:
				app = new AdminApp();
				break;
			case 3:
				app = new GuestApp();
				break;
			}
			if(app!= null)
				app.init();
		}	
	}
}