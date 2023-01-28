package com.user_interface;

import com.enums.Gender;
import com.enums.SeatType;
import com.exceptions.PasswordPatternException;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Helper {
	static private final Scanner scan = new Scanner(System.in);
	static String getStringInput() {
		String string = null;
		try {
			 string = scan.next();

		}catch (Exception e){
		     string = getStringInput();
		}
		return string;
	}
	static int getIntegerInputInARange(int lowerBound , int upperBound){
		int value =0;
		try {
			 String data = scan.nextLine(); // use lineInput and add validation for checking data type
			 String regex = "\\d+";
			 if(!Pattern.matches(regex,data))
				 throw new InputMismatchException();
			 value = Integer.parseInt(data);
			if(value < lowerBound || value > upperBound )
				throw new Exception();

		}catch(InputMismatchException m){
			System.out.println("Enter only integer data type");
			value = getIntegerInputInARange(lowerBound, upperBound);
		}
		catch (Exception e){
			System.out.println("Please enter value in the range of [" +lowerBound +"-"+ upperBound+"]  inclusively");
			value = getIntegerInputInARange(lowerBound,upperBound);
		}
		return value;
	}
	static int getIntegerInput() {

		int value = 0;
		try {
			String integer = "";
			integer = scan.nextLine();
			if(!Pattern.matches("\\d+", integer))
				throw new Exception();
			value = Integer.parseInt(integer);
		}catch(Exception e) {
			//System.out.println(PrintStatements.VALID_INTEGER_INPUT);
			value = getIntegerInput();
		}
		return value;
	}
	static String getYesOrNo(){
		String yesOrNo ="";
		try{
			 yesOrNo = scan.nextLine();
			if(!(yesOrNo.equalsIgnoreCase("yes") || yesOrNo.equalsIgnoreCase("no"))){
				throw new Exception();
			}

		}catch(Exception e){
			yesOrNo = getYesOrNo();
		}
		return yesOrNo;
	}

	static char getCharacterInput() {
		char character = '\n';
		try{
			character = scan.nextLine().charAt(0);
		} catch (Exception e){
			character = getCharacterInput();
		}
		return character;
	}
	static String getLineInput() {
		String line="";
		try{
			line = scan.nextLine();
			String pattern ="\n";
			if(Pattern.matches(pattern, line))
				throw new Exception();

		}catch(Exception e){
			line = getLineInput();
		}
		return line;
	}
	static Long getLongInput() {

		long number = 0;
		try {
			number = scan.nextLong();
		}catch (Exception e){
			number = getLongInput();
		}
		return number;
	}
	static String getPassword() {
		String password = "";
		try {
			String pattern = "";
			password = scan.nextLine();
			if (!Pattern.matches(pattern, password))
				throw new PasswordPatternException(PrintStatements.PASSWORD_RULES);

		} catch (PasswordPatternException p) {
			System.out.println(p);
			password = getPassword();
		}
		return password;
	}
	static LocalDate getJourneyDate(){
		LocalDate journeyDate = null;
		try {
			String regex = "[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}";
			String date = getLineInput();
			if(!Pattern.matches(regex, date))
				throw new Exception();
			journeyDate = LocalDate.parse(date);
			LocalDate upperBoundDate = LocalDate.now().plusDays(120);
			if ((journeyDate.isAfter(upperBoundDate) || journeyDate.isEqual(upperBoundDate) || journeyDate.isBefore(LocalDate.now()))) {
				throw new Exception();
			}


		}catch(Exception d){
			//System.out.println(d.getMessage());
			//System.out.println("failed  date input");
			System.out.println(PrintStatements.DATE_VALIDATION);
			journeyDate = getJourneyDate();
		}
		return journeyDate;
	}
	static LocalDate getDateInput(){
		LocalDate date = null;
		try {
			String regex = "^[1-9][0-9]{3}-(([0-1][0-2])|0[0-9])-(([0-2][0-9])|3[01])$";
			String dateOfBirth = getStringInput();
			if(!Pattern.matches(regex, dateOfBirth))
				throw new Exception();
			date = LocalDate.parse(dateOfBirth);
			if (date.isAfter(LocalDate.now()))
				throw new Exception();
		}catch(Exception d){
			//System.out.println(d.getMessage());
			System.out.println("failed  date input");
			date = getDateInput();
		}
		return date;
	}
	static String getEmailInput(){
		String email= "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		try {
			email = getStringInput();
			String pattern = "";
			if (!Pattern.matches(pattern, email))
				throw new Exception();
		}catch(Exception e){
			System.out.println("Please enter a valid mail");
			email = getEmailInput();
		}
		return email;
	}
	static String getPhoneNumber(){
		String phone ="";
		try{
			phone = getStringInput();
			String phonePattern = "[1-9][0-9]{9}";
			if(!Pattern.matches(phonePattern, phone))
				throw new Exception();

		}catch(Exception e){
			System.out.println(PrintStatements.PHONE_ERROR);
			phone = getPhoneNumber();
		}
		return phone;
	}
	static Gender getGender(){
		Gender gender = Gender.MALE;
		try{
			System.out.println(PrintStatements.GENDER_OPTIONS);
			char gen = getCharacterInput();
			switch(gen){
				case 'M':
				case 'm':
					gender = Gender.MALE;
					break;
				case 'F':
				case 'f':
					gender = Gender.FEMALE;
					break;
				case 'O':
				case 'o':
					gender = Gender.OTHERS;
					break;
				default:
					throw new Exception();

			}
		}catch(Exception e){
			gender = getGender();

		}
		return gender;
	}
	static SeatType getSeatType(){
		System.out.println(PrintStatements.SEAT_PREFERENCE );
		String seatInput = Helper.getLineInput();
		SeatType seatType = null;
		try {
			if(seatInput.equalsIgnoreCase("LB"))
				seatType = SeatType.LB;
			else if(seatInput.equalsIgnoreCase("MB"))
				seatType = SeatType.MB;
			else if(seatInput.equalsIgnoreCase("UB"))
				seatType = SeatType.UB;
			else if(seatInput.equalsIgnoreCase("SUB"))
				seatType = SeatType.SUB;
			else if(seatInput.equalsIgnoreCase("SLB"))
				seatType = SeatType.SLB;
			else if(seatInput.equalsIgnoreCase("None"))
				seatType = null;
			else
				throw new Exception ();
		}catch (Exception e){
			System.out.println("Please enter as Mentioned below");
			seatType = getSeatType();
		}
		return seatType;
	}

}
