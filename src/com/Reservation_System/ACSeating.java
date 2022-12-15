package com.Reservation_System;

import java.util.ArrayList;
import java.util.HashMap;

public class ACSeating extends Seating {
	private boolean foodAvailabile;
	private boolean MagzinesAvailabile;
	public ACSeating(HashMap<String, ArrayList<Integer>> bookedSeats) {
		super(bookedSeats);
		foodAvailabile = true;
		MagzinesAvailabile = true;
	}
	public boolean isFoodAvailabile() {
		return foodAvailabile;
	}

	public void setRequireFood(boolean foodAvailabile) {
		this.foodAvailabile = foodAvailabile;
	}
	public boolean AreMagzinesAvailabile() {
		return MagzinesAvailabile;
	}
	public void setMagzinesAvailabile(boolean magzineAvailability) {
		MagzinesAvailabile = magzineAvailability;
	}
	
	
}
