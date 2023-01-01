package com.Reservation_System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.Reservation_System.Enum.SeatType;

public class SeatAllocationHelper {
	private int coachCount;
	private int seatsInCoach;
	
	private final HashMap<Integer, ArrayList<Integer >> bookedSeats = new HashMap<>();
	private final HashMap<Integer, ArrayList<Integer>> seatAndDestinationMapping = new HashMap<>();
	private final HashMap<Integer, ArrayList<Integer>> seatAndSourceMapping = new HashMap<>();
	private int availableSeats;
	
	SeatAllocationHelper( int coachCount, int seatsInCoach){
		this.coachCount = coachCount;
		this.seatsInCoach = seatsInCoach;
		this.availableSeats = coachCount * seatsInCoach;	
	}
	public void increaseCoach(int count) {
		coachCount+= count;
		this.availableSeats+=(coachCount*seatsInCoach );
		
	}

	/*
	 * public void incrementAvailableSeats() { if(coaches.size()> 0 &&
	 * this.availableSeats < coaches.size()* coaches.get(0).getSeatsCount())
	 * this.availableSeats++; } public void decrementAvailableSeats() {
	 * if(coaches.size()>0 && this.availableSeats > 0 ) { this.availableSeats--; } }
	 */
	public int getAvailableSeats() {
		return this.availableSeats;
		
	}
	public HashMap<Integer, ArrayList<Integer>> getBookedSeats(){
		return bookedSeats;
		
	}
	public String searchSeat() {
		String seatId ="";
		return seatId;
	}
	
	public int[] lastAssigned = {0,0,0,0,0};
	
	public int[]berthCount = { (availableSeats%8)*2,(availableSeats%8)*2, (availableSeats%8)*2,(availableSeats%8),(availableSeats%8)};
	public List<List<Integer>> searchInBookedSeats(int index, int source, int destination, int trainSource, int trainDestination){
		List<Integer> matchedPreference= new ArrayList<>();
		List<Integer> otherSeats = new ArrayList<>();
		List<List<Integer>>availableSeatsInBookedSeats =new ArrayList<>();
		availableSeatsInBookedSeats.add(matchedPreference);
		availableSeatsInBookedSeats.add(otherSeats);
		boolean found = false;
		while(source >= trainSource && !found) {
			List<Integer>potentialSeatNumbers = bookedSeats.get(source);
			for(Integer seatNo: potentialSeatNumbers) {
				List<Integer>multipleDestinations = seatAndDestinationMapping.get(seatNo);
				int currentStation = source;
				currentStation++;
				while(!multipleDestinations.contains(currentStation) && currentStation<= trainDestination ) {
					currentStation++;
				}
				if(currentStation > trainDestination) {
					if ((seatNo%seatsInCoach)%8 == index || ((seatNo%seatsInCoach)%8)+3 == index ) {
						matchedPreference.add(seatNo);
					}
					else {
						otherSeats.add(seatNo);
					}
					found = true;
				}
					
			}
			source--;
		}
		return availableSeatsInBookedSeats;
	}
	
	public String findAppropriateSeat(SeatType seatPreference, int source, int destination, int trainSource, int trainDestination) {
		String seat="";
		if(seatPreference == null) {
			
		} 
		else {
			int index =-1;
			if(seatPreference == SeatType.LB)
				index = 0;
			else if(seatPreference == SeatType.MB)
				index = 1;
			else if(seatPreference == SeatType.UB)
			   index =2;
			else if(seatPreference == SeatType.SLB)
				index = 3;
			else if(seatPreference == SeatType.SUB)
				index = 4;
			List<List<Integer>>seatFromBookedSeats = searchInBookedSeats(index, source,destination, trainSource, trainDestination )
			if(berthCount[index]> 0 )
				
			return seat;
		}
	}
}
 