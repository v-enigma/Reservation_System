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
	static int getIntegerInput() {

		int value = 0;
		try {
			String integer = "";
			integer = scan.nextLine();
			if(!Pattern.matches("[0-9]+", integer))
				throw new Exception();
			value = Integer.parseInt(integer);
		}catch(Exception e) {
			value = getIntegerInput();
		}
		return value;
	}
	static char getCharacterInput() {
		char character = '\n';
		try{
			character = scan.next().charAt(0);
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
			scan.nextLong();
		}catch (Exception e){
			getLongInput();
		}
		return number;
	}
	static String getPassword() {
		String password = "";
		try {
			String pattern = "";
			password = scan.nextLine();
			if (!Pattern.matches(pattern, password))
				throw new PasswordPatternException();
		} catch (InputMismatchException e) {
			System.out.println("Please enter only Strings");
			password = getPassword();
		} catch (PasswordPatternException p) {
			System.out.println(PrintStatements.PASSWORD_RULES);
			password = getPassword();
		}
		return password;
	}

	static LocalDate getDateInput(){
		LocalDate date = null;
		try {
			String dateOfBirth = "";
			dateOfBirth = getStringInput();
			date = LocalDate.parse(dateOfBirth);
			if (date.isAfter(LocalDate.now()))
				throw new Exception();
		}catch(Exception d){
			//System.out.println(d.getMessage());
			date = getDateInput();
		}
		return date;
	}
	static String getEmailInput(){
		String email= "";
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
			String phonePattern = "[6-9][0-9]{9}";
			if(!Pattern.matches(phonePattern, phone))
				throw new Exception();

		}catch(Exception e){
			System.out.println("Phone number cannot be greater than 10 digits. It cannot contain characters");
			phone = getPhoneNumber();
		}
		return phone;
	}
	static Gender getGender(){
		Gender gender = Gender.MALE;
		try{
			System.out.println("Enter gender.\nM for Male.\nF for Female.\nO for Others.");
			char gen = getCharacterInput();
			switch(gen){
				case 'M':
					gender = Gender.MALE;
					break;
				case 'F':
					gender = Gender.FEMALE;
					break;
				case 'O':
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
		String seatInput = Helper.getStringInput();
		SeatType seatType = null;
		try {
			switch (seatInput) {
				case "LB":
					seatType = SeatType.LB;
					break;
				case "MB":
					seatType = SeatType.MB;
					break;
				case "UB":
					seatType = SeatType.UB;
					break;
				case "SUB":
					seatType = SeatType.SUB;
					break;
				case "SLB":
					seatType = SeatType.SLB;
					break;
				default:
					throw new Exception();
			}
		}catch (Exception e){
			seatType = getSeatType();
		}
		return seatType;
	}
}
