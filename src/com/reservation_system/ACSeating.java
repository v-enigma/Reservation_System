package com.reservation_system;

public class ACSeating extends Seating {
	private boolean foodAvailable;
	private boolean MagazinesAvailable;
	public ACSeating(  ) {
		super();
		foodAvailable = true;
		MagazinesAvailable = true;
	}

	public boolean isFoodAvailable() {
		return foodAvailable;
	}

	public void setRequireFood(boolean foodAvailable) {
		this.foodAvailable = foodAvailable;
	}
	public boolean AreMagazinesAvailable() {
		return MagazinesAvailable;
	}
	public void setMagazinesAvailable(boolean magazineAvailability) {
		MagazinesAvailable = magazineAvailability;
	}
	
	
}
