package com.user_interface;
public class Main{
	public static void main(String args[]) {
		
		System.out.println(PrintStatements.WELCOME_MESSAGE);
		boolean run = true;
		while(run) {
			System.out.println(PrintStatements.APP_OPTIONS);
			int option = Helper.getIntegerInput();
			
			switch(option) {
			case 1:
				(new CustomerApp()).init();
				break;
			case 2:
				new AdminApp().init();
				break;
			case 3:
				(new GuestApp()).init();
				break;
			case 4:
				run = false;
				break;
			}
			
		}	
	}
}