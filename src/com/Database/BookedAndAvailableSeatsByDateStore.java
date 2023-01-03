package com.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.Reservation_System.Enum.SeatType;

public class BookedAndAvailableSeatsByDateStore{ // Store booked seats and available seats for a given date
	private int numCoaches;
	private int seatsPerCoach;
	
	private final HashMap<Integer, ArrayList<Integer >> bookedSeatMap = new HashMap<>();
	private final HashMap<Integer, ArrayList<Integer>> seatDestinationMap = new HashMap<>();
	//private final HashMap<Integer, ArrayList<Integer>> seatAndSourceMapping = new HashMap<>();
	private int numavailableSeats;
	public int[] lastAssignedSeats = {0,0,0,0,0};
	public int[]berthAvailability = { (numavailableSeats%8)*2,(numavailableSeats%8)*2, (numavailableSeats%8)*2,(numavailableSeats%8),(numavailableSeats%8)};
	public int racSeatAvailability ;
	public BookedAndAvailableSeatsByDateStore( int coachCount, int seatsInCoach){
		this.numCoaches = coachCount;
		this.seatsPerCoach = seatsInCoach;
		this.numavailableSeats = coachCount * seatsInCoach;	
		this.racSeatAvailability = numavailableSeats%8;
	}
	public void addCoach(int count) {
		numCoaches+= count;
		this.numavailableSeats+=(numCoaches*seatsPerCoach);
		
	}
	
	/*
	 * public void incrementAvailableSeats() { if(coaches.size()> 0 &&
	 * this.availableSeats < coaches.size()* coaches.get(0).getSeatsCount())
	 * this.availableSeats++; } public void decrementAvailableSeats() {
	 * if(coaches.size()>0 && this.availableSeats > 0 ) { this.availableSeats--; } }
	 */
	public int getAvailableSeats() {
		return this.numavailableSeats;
		
	}
	public HashMap<Integer, ArrayList<Integer>> getBookedSeats(){
		return bookedSeatMap;
		
	}
	public List<List<Integer>> findAvailableSeatsInBookedMap(int index, int source, int destination,  int trainDestination){
		List<Integer> matchedPreference= new ArrayList<>();
		List<Integer> otherSeats = new ArrayList<>();
		List<List<Integer>>availableSeatsInBookedSeats =new ArrayList<>();
		availableSeatsInBookedSeats.add(matchedPreference);
		availableSeatsInBookedSeats.add(otherSeats);
		boolean found = false;
		while(source >= 0 && !found) {
			List<Integer>potentialSeatNumbers = bookedSeatMap.get(source);
			for(Integer seatNo: potentialSeatNumbers) {
				List<Integer>multipleDestinations = seatDestinationMap.get(seatNo);
				int currentStation = source;
				currentStation++;
				while(!multipleDestinations.contains(currentStation) && currentStation<= trainDestination ) {
					currentStation++;
				}
				if(currentStation > trainDestination) {
					if ((seatNo%seatsPerCoach)%8 == index || ((seatNo%seatsPerCoach)%8)+3 == index ) {
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
	public Integer findAvailableSeatFromUnoccupied(int index) {
		int seat =-1;
		if(index ==-1) {
			index = 0;
			while(!(lastAssignedSeats[index] > 0)) {
				index++;
			}
			if(index>=5 ) {
				return seat;
			}
		}
	
		int tempSeat = lastAssignedSeats[index];
		if(tempSeat == 0 && index ==0)
			tempSeat =3;
		else if(tempSeat%8 == 0 || tempSeat%8 == 1 || tempSeat %8== 2) {
			tempSeat+=3;
		}
		else if(tempSeat%8 == 3 || tempSeat%8 == 4 || tempSeat%8 == 5 ) {
			tempSeat+=5;
		}
		else {
			tempSeat+= 8;
		}
		berthAvailability[index]--;
		seat = tempSeat;
		lastAssignedSeats[index]= seat;
	
		return seat;
}
	public Integer findAppropriateSeat(SeatType seatPreference, int source, int destination, int trainDestination) {
		Integer seat= -1;
		int index = -999;
		if(seatPreference == null) {
			
			index =-1;
			seat = findAvailableSeatFromUnoccupied(index);
		} 
		
			
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
		List<List<Integer>>seatFromBookedSeats = findAvailableSeatsInBookedMap(index, source,destination, trainDestination );
		if(berthAvailability[index]> 0 || seatFromBookedSeats.get(0).size() >0 ) {
			if(seatFromBookedSeats.get(0).size()> 0) {
				seat = seatFromBookedSeats.get(0).get(0);
			}
			else {
				int tempSeat = lastAssignedSeats[index];
				if(tempSeat == 0 && index ==0)
					tempSeat =3;
				else if(tempSeat%8 == 0 || tempSeat%8 == 1 || tempSeat %8== 2) {
					tempSeat+=3;
				}
				else if(tempSeat%8 == 3 || tempSeat%8 == 4 || tempSeat%8 == 5 ) {
					tempSeat+=5;
				}
				else {
					tempSeat+= 8;
				}
				berthAvailability[index]--;
				seat = tempSeat;
				lastAssignedSeats[index]= seat;
				
			}
		
		}
		else {
			if(seatFromBookedSeats.get(1).size()> 0)
				seat = seatFromBookedSeats.get(1).get(0);
			else {
				seat = findAvailableSeatFromUnoccupied(index);
			}
		}
		
		return seat;
	}
	
}
 