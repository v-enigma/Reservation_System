package com.database; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.enums.SeatType;



public final class  BookedAndAvailableSeatsByDate{ // Store booked seats and available seats for a given date
	private int numCoaches;
	private final int seatsPerCoach;
	
	private final HashMap<String, ArrayList<Integer >> destinationSeatMap = new HashMap<>(); // destinationCode is key  and all passenger seat Ids that get down will be the values
	private final HashMap<Integer, ArrayList<String>> seatAndDestinationMap = new HashMap<>();
	private final HashMap<Integer, ArrayList<String>> seatAndSourceMap = new HashMap<>();
	private int numAvailableSeats;
	private final  Stack<Integer>[] bb = new Stack[6];
	private void init(int numAvailableSeats){
		bb[0] = new Stack<>();
		bb[1] = new Stack<>();
		bb[2] = new Stack<>();
		bb[3] = new Stack<>();
		bb[4] = new Stack<>();
		bb[5] = new Stack<>();
		for(int i=1;i <= numAvailableSeats; i++){
			int index = i%8;
			if(index == 1 || index == 4)
				bb[0].add(i);
			else if(index == 2 || index == 5)
				bb[1].add(i);
			else if( index == 3 || index == 6)
				bb[2].add(i);
			else if(index == 7)
				bb[3].add(i);
			else {
				
					bb[4].add(i);
			}

		}
	}
	//private  final int[] berthAvailability ;
	private final int waitingListCapacity ;
	private int currentWL; //current waiting list index
	public BookedAndAvailableSeatsByDate( int coachCount, int seatsInCoach){
		this.numCoaches = coachCount;
		this.seatsPerCoach = seatsInCoach;
		this.numAvailableSeats = coachCount * seatsInCoach;
		this.waitingListCapacity = ((numAvailableSeats)%0.01 > 10) ? (int)(numAvailableSeats%0.01) : 10 ;
		this.currentWL = 0;
		init(numAvailableSeats);
		//berthAvailability = new int[]{(numAvailableSeats % 8) * 2, (numAvailableSeats % 8) * 2, (numAvailableSeats % 8) * 2, (numAvailableSeats % 8), (numAvailableSeats % 8)};
	}
	private void updateDestinationSeatMap(String stationCode, int seatNo){
		if(destinationSeatMap.containsKey(stationCode)){
			destinationSeatMap.get(stationCode).add(seatNo);
		}else{
			ArrayList<Integer> seatList = new ArrayList<>();
			seatList.add(seatNo);
			destinationSeatMap.put(stationCode, seatList);
		}
	}
	private void updateSeatAndDestinationMap(String stationCode, int seatNo){
		if(seatAndDestinationMap.containsKey(seatNo)){
			seatAndDestinationMap.get(seatNo).add(stationCode);
		}else{
			ArrayList<String> stations = new ArrayList<>();
			stations.add(stationCode);
			seatAndDestinationMap.put(seatNo, stations);
		}


	}
	private void updateSeatAndSourceMapping(String stationCode, int seatNo ){
		if(seatAndSourceMap.containsKey(seatNo)){
			seatAndSourceMap.get(seatNo).add(stationCode);
		}else{
			ArrayList<String> stations = new ArrayList<>();
			stations.add(stationCode);
			seatAndSourceMap.put(seatNo, stations);
		}
	}
	private void updateSeatStorage(int seatNo,String sourceCode,String destinationCode){
		updateDestinationSeatMap(destinationCode, seatNo);
		updateSeatAndDestinationMap(destinationCode,seatNo);
		updateSeatAndSourceMapping(sourceCode,seatNo);

	}

	public void addCoach(int count) {
		numCoaches+= count;
		this.numAvailableSeats+=(numCoaches*seatsPerCoach);
		
	}

	public int getAvailableSeats() {
		int avlSeats =0;
		for(Stack<Integer> temp : bb){
			avlSeats+=temp.size();
		}

		return avlSeats;
	}

	public List<List<Integer>> findAvailableSeatsInDestinationMap(int index, String sourceCode, String destinationCode, List<String>stopsCodes){
		int sPointer = stopsCodes.indexOf(sourceCode);
		int dPointer = stopsCodes.indexOf(destinationCode);
		List<Integer> matchedPreference = new ArrayList<>();
		List<Integer> otherSeats = new ArrayList<>();
		List<List<Integer>>availableSeatsInBookedSeats = new ArrayList<>();
		/*availableSeatsInBookedSeats.add(matchedPreference);
		availableSeatsInBookedSeats.add(otherSeats);*/
		boolean found = false;
		while(sPointer >= 0 && !found) {
			sourceCode = stopsCodes.get(sPointer);
			List<Integer>potentialSeatNumbers = destinationSeatMap.get(sourceCode);
			if(potentialSeatNumbers!= null)
				for(Integer seatNo: potentialSeatNumbers) {
					List<String>multipleDestinations = seatAndDestinationMap.get(seatNo);
					int currentStationPointer = sPointer;
					currentStationPointer++;
					String station = stopsCodes.get(currentStationPointer);
					while(currentStationPointer <= stopsCodes.size()-1){
						station = stopsCodes.get(currentStationPointer);
						if(multipleDestinations.contains(station)   ){
							if(currentStationPointer<= dPointer){
								//already booked seat
								currentStationPointer = -1;
								break;
							}
							else{
								int stationIndex = multipleDestinations.indexOf(station);
								String potentialSeatSource = seatAndSourceMap.get(seatNo).get(stationIndex);
								int potentialSeatSourceIndex = stopsCodes.indexOf(potentialSeatSource);
								if(potentialSeatSourceIndex < dPointer) {
									// already booked seat
									currentStationPointer =-1;

								}
								else{
									currentStationPointer = stopsCodes.size();
								}

							}
							break; //

						}
						currentStationPointer++;
					}
					while(!multipleDestinations.contains(station) && currentStationPointer <= stopsCodes.size()-1  && currentStationPointer >= 0 ) {
						currentStationPointer++;
						station = stopsCodes.get(currentStationPointer);
					}
					if(currentStationPointer > stopsCodes.size()-1) {
						if(index == -1) {
							found = true;
							otherSeats.add(seatNo);
						}
						else if ((seatNo%seatsPerCoach)%8 == index-1|| ((seatNo%seatsPerCoach)%8)+5 == index-1 ||
								((seatNo%seatsPerCoach)%8 == 0 && index == 4) || ((seatNo%seatsPerCoach)%8 == 7 && index == 3)){
							found = true;
							matchedPreference.add(seatNo);
						}
						else {
							otherSeats.add(seatNo);
						}

					}

				}
			sPointer--;

			}
		if(matchedPreference.size() > 0 || otherSeats.size() > 0) {
			availableSeatsInBookedSeats.add(matchedPreference);
			availableSeatsInBookedSeats.add(otherSeats);
		}
		return availableSeatsInBookedSeats;
	}
	public int findWaitingListSeats(){
		if(currentWL < waitingListCapacity){
			return ++currentWL;
		}
		return -1;
	}
	public int findRACSeats(){
		int index = 4;
		int potentialRACSeat = -1;
		if(bb[index].size()>0 ){
			if(bb[index].peek()%8 == 1){
				potentialRACSeat = bb[index].pop();
				potentialRACSeat--;
			}
			else{
				potentialRACSeat = bb[index].pop();
				Stack<Integer> popped = new Stack<>();
				while(bb[index].size()> 0 && (bb[index].peek())%8 == 0){
					popped.push(bb[index].pop());
				}
				bb[index].push(potentialRACSeat + 1) ;
				while(popped.size()> 0){
					bb[index].push(popped.pop());
				}
			}
		}
		return potentialRACSeat;
	}
	private int getSeatFromBookedSeats(int index, List<List<Integer>> seatFromBookedSeats){ // The Index is -1 for no preference
		if(index == -1){
			if(seatFromBookedSeats.get(1).size()> 0)

				return seatFromBookedSeats.get(1).get(0);
			else{
				return -1;
			}
		}
		else{
			if(seatFromBookedSeats.get(0).size() > 0){
				return seatFromBookedSeats.get(0).get(0);
			}
			else{
				return -1;
			}
		}
	}
	public Integer findAvailableSeatFromUnoccupied(int index) {
		int seat =-1;
		if(index == -1) {
			index = 0;
			while(! (index < 5 && bb[index].size() > 0)) {
				index++;
				if(index == 5)
					break;
			}
			if(index == 5 ) {
				return seat;
			}
		}
	
		int tempSeat = bb[index].pop();

		if(index == 4 && tempSeat%8 == 1 ){
				bb[index].push(tempSeat);
				tempSeat =-1;
			}

		seat = tempSeat;

		/*if(seat > 0){
            updateSeatStorage(seat, sourceCode,destinationCode);
		}*/
		return seat;
	}

	public Integer findAppropriateSeat(SeatType seatPreference, String sourceCode, String destinationCode, List<String>stopCodes) {
		Integer seat =-1;
		int index = -999;
		if(seatPreference == null)
			index =-1;
		else if (seatPreference == SeatType.LB)
				index = 0;
		else if (seatPreference == SeatType.MB)
			index = 1;
		else if (seatPreference == SeatType.UB)
			index = 2;
		else if (seatPreference == SeatType.SUB)
			index = 3;
		else if (seatPreference == SeatType.SLB)
			index = 4;
		List<List<Integer>> seatFromBookedSeats = findAvailableSeatsInDestinationMap(index, sourceCode, destinationCode, stopCodes);
		if( seatFromBookedSeats.size() > 0 ) {
			seat = getSeatFromBookedSeats(index, seatFromBookedSeats);

		}
		if(seat < 0){
			seat = findAvailableSeatFromUnoccupied(index);
		}
		if(seat < 0 && index != -1 && seatFromBookedSeats.get(1).size()>0)
			seat  = seatFromBookedSeats.get(1).get(0);
		updateSeatStorage(seat, sourceCode,destinationCode);
	  return seat;
	}
	public void freeSeat(String sourceCode, String destinationCode, int seatNo){
		destinationSeatMap.get(destinationCode).remove((Object)seatNo);
		if(isSeatHasOneBooking(seatNo)){
			int index = -1 ;
			switch(seatNo%8){
				case 1 : case 4 :
					index = 0;
					break;
				case 2 : case 5 :
					index = 1;
					break;

				case 3 : case 6 :
					index  = 2;
					break;
				case 7 :
					index  = 3;
					break;

				case 0 :
					index = 4;
					break;

			}

			bb[index].push(seatNo);

		}
		seatAndDestinationMap.get(seatNo).remove(destinationCode);
		seatAndSourceMap.get(seatNo).remove(sourceCode);

	}

	private boolean isSeatHasOneBooking(int seatNo){
		return seatAndDestinationMap.get(seatNo).size() == 1 ;
	}
}
 