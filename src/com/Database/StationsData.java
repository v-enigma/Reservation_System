package com.Database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.Reservation_System.Station;
import com.Reservation_System.Enum.StationType;

public class StationsData {
	private static final StationsData STATION_DB = new StationsData();
	private StationsData() {
		addStations();
	}
	public static StationsData getInstance() {
		return STATION_DB;
	}
	private final List<Station>stations = new ArrayList<>();
	
	public Station addStation(String name, String code,int noOfPlatforms, StationType stype ) {
		Station station = new Station(name, code, noOfPlatforms, stype);
		stations.add(station);
		return station;
	}
	public Station findStation(String sName) {
		Station station= null;
		Iterator<Station> stationIterator = stations.iterator(); 
		while(stationIterator.hasNext()) {
			station = stationIterator.next();
			if(station.getName().equalsIgnoreCase(sName)) {
				break;
			}
		}
		return station;
	}
	// dummy initialization
	private void addStations() {
		int count  =11;
		char st ='A';
		int i = 4;
		StationType  stype= null ;
		while (count >0) {
			if(i%4 == 0) {
				stype = StationType.CENTRAL;
			}
			else if(i %4 ==1)
				stype = StationType.JUNCTION;
			else if(i%4 ==2)
				stype = StationType.STATION;
			else 
				stype = StationType.TERMINUS;
			Station station  = new Station(String.valueOf(st), String.valueOf(st),i, stype  );
			stations.add(station);
			if(i%4 ==0) 
				i-=2;
			
			else  if(i%3 ==0)
				i+=3;
			else 
				i++;
			
			count--;
			st++;	
		}
	}
	public String getStationCode(String stationName) {
		String stationId = "";
		for(Station station: stations) {
			if(station.getName().equals(stationName))
				return station.getId();
		}
		return stationId;
		
	}
}
