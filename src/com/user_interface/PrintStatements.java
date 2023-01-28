package com.user_interface;

 final class  PrintStatements {
	 //--------------------------------------------------------GENERIC MESSAGES------------------------------------------------------
	 static final String WELCOME_MESSAGE = "Welcome to Reservation System";
	 static final String APP_OPTIONS = "Choose your option \n 1.Customer  \n 2.Admin \n 3.Guest \n 4.Exit\n";
	 static final String LOGIN_FAILURE = "Login failure. Please try agian";
	 static final String GUEST_OPTIONS = "Enter your option \n 1.To search trains. \n 2. To PNR Search\n 3. To Exit\n";
	 static final String USER_NOT_AVAILABLE = "UserId is not Available. Please try another userId ";
	 static final String PASSWORD_RULES = "SHOULD CONTAIN AT LEAST 8 CHARACTERS LONG.\n. SHOULD CONTAIN CAPITAL LETTER , SMALL CASE LETTERS, SPECIAL CHARACTERS AND NUMBERS\n. SHOULD NOT CONTAIN WHITE SPACES ";
	 static final String DATE_OF_BIRTH = "Enter Date of Birth in YYYY-MM-DD";
	 static final String VALID_STATION = "Enter valid  station";
	 static final String TIME_FORMAT = "Enter time(HH:MM) in 24HRS format";
	 static final String OPTIONS = "Enter your code\n1.SignIn\n2.SignUp\n3.Exit";
	 static final String GENDER_OPTIONS = "Enter gender.\nM for Male.\nF for Female.\nO for Others.";
	 static final String PHONE_ERROR = "Phone number cannot be greater than 10 digits. It cannot contain characters";
	 static final String VALID_INTEGER_INPUT ="Please enter valid integer input";
	 //-------------------------------------------------------CUSTOMER RELATED MESSAGES-----------------------------------------------
	 static final String PNR_SEARCH = "Enter PNR to know the booking status";
	 static final String PNR_ERROR = "Invalid PNR. Please Enter correct PNR.";
	 static final String JOURNEY_DETAILS = "Enter source, destination station  and  date of journey\\n ";
	 static final String DATE_VALIDATION = "Enter a valid Date. You are allowed to book only 4 months ahead from today";
	 static final String CUSTOMER_OPTIONS = "1. Search Train\n 2.BookTicket \n 3.Cancel \n 4.Past Bookings \n 5.PNR Status\n 6.Update Details\n 7.Display User Profile\n 8.Exit  ";
	 static final String DETAILS_UPDATE = "Enter the option for which you want to update the details.\n1.to update name.\n2.To update email\n.3.To update phone number.\n 4.To update seat Preference \n 5.To update date of birth";
	 static final String NO_TRAINS = "NO TRAINS AVAILABLE BETWEEN  GIVEN SOURCE AND DESTINATION";
	 static final String SEAT_PREFERENCE = "Enter seat Preference.\nLB for LowerBerth.\nMB for MiddleBerth.\nUB for UpperBerth.\nSUB for Side Upper Berth.\nSLB for Side lower Berth\n None for No preference";
	 static final String TRAINS_AVAILABLE = "Available trains for the given train Number";
	 static final String CANCEL_PNR = "PLEASE PROVIDE PNR TO CANCEL TICKET";
	 static final String BOOKING_CLASS = "Enter booking class.\n 1.Sleeper \n 2.AC  ";
	 static final String EMPTY_BOOKINGS = "PAST BOOKINGS ARE EMPTY.";
	 static final String PASSENGERS_COUNT = "You can book at max for 6 persons  at a time";
	 static final String DESTINATION  = "Enter the destination station name";
	 static final String SOURCE ="Enter the source station name";
	 //------------------------------------------------------ADMIN RELATED MESSAGES---------------------------------------------------
	 static final String TRAIN_INDEX = "Enter the index of the train printed above to book seats ";

	 static final String STATION_NOT_FOUND = "There is no such station in my storage.\n Do you like to add new station? If yes press 'Y' else 'N'." ;
	 static final String STATION_TYPE = "Enter station type .\n J for Junction.\n T for Terminus.\n C for Central.\n  S for station. \n";

	 static final String TOP_HEADING = "SLNO\t Train Number \t Train Name";
	 static final String SCHEDULE_TRAIN = "Enter the Train Number you want to schedule";

	 static final String TRAIN_NUMBER_ERROR = "Train with the given number does not exist";
	 static final String REG_ID = "Enter your train RegId you want to schedule";
	 static final String FREQUENCY = "Enter No of days the train will run in a week .";
	 static final String  TRAIN_NUMBER = "Enter the train number";
	 static final String TRAIN_NAME = "Enter the name of the train";
	 static final String TRAIN_SOURCE = "Enter the source  of the train ";

	 static final String TRAIN_DESTINATION ="Enter the destination  of the train ";
	 static final String AC_COACH_COUNT = "Enter no of AC coaches";
	 static final String SLEEPER_COACH_COUNT = "Enter no of Sleeper coaches";
	 static final String REG_ID_ERROR ="PLEASE ENTER CORRECT  ID FROM THE ABOVE TRAINS";
	 static final String WEEK_DAY = "Enter the name of the day the train will run in a week ?";
	 static final String REMOVE_TRAIN = "Enter the train No you want to remove";
	 static final String WEEK_ERROR = "Please Enter valid day of the week.";
	 static final String DAY_NAME = "Enter Name of the day in week period";
	 static final String ADMIN_OPTIONS = "1.Add Train\n2.Schedule Train\n3.Remove Train\n4.Print Chart.\n5.Exit";
	 static final String GET_OPTION = "Enter your option ";
	 static final String DATE_FORMAT = "Enter date of journey.Enter the date in YYYY-MM-DD format";


 }

