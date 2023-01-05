package com.user_interface;

public class GuestApp  implements Application , Searchable{

	@Override
	public void init() {
		System.out.println(PrintStatements.GUEST_OPTIONS);
		boolean run = true;
		
		while(run) {
			int option = Helper.getIntegerInput();
			switch(option) {
				case 1:
					search();
					break;
				case 2:
					pnrSearch();
					break;
				case 3:
					run = false;
					break;
			}
		
		}
		
	}

}
