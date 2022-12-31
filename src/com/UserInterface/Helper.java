package com.UserInterface;

import java.util.Scanner;

public class Helper {
	static private Scanner scan = new Scanner(System.in);
	static String getStringInput() {
		String string = scan.next();
		return string;
	}
	static int getIntegerInput() {
		int integer = scan.nextInt();
		return integer;
	}
	static char getCharacterInput() {
		char character = scan.next().charAt(0);
		return character;
	}
	static String getLineInput() {
		String empty = scan.nextLine();
		return empty;
	}

}
