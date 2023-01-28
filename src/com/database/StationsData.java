package com.database;

import java.util.*;

import com.reservation_system.Station;
import com.enums.StationType;
import sun.security.provider.certpath.AdjacencyList;

public class StationsData {
	private static final StationsData STATION_DB = new StationsData();
	//private final String[] stationCodes = {};

	//private final String[] names = {"Wadi", "Hyderabad","Secunderabad","Nellore"};


	/*private final  HashMap<String, Set<Edge>> railwayNetwork = new HashMap<>();
	private void populate(){
		for(String name : names){
			railwayNetwork.put(name, new HashSet<>());
		}


	}*/
	/*List<List<String>> findAllPaths(String start, String end){
		HashSet<String> visited = new HashSet<>();
		ArrayList<String> pathList = new ArrayList<>();
		pathList.add(start);
		List<List<String>> allPaths = new ArrayList<>();
		findAllPathsUtils(start, end, visited, pathList, allPaths);
		return allPaths;
	}
	void findAllPathsUtils( String start, String end, HashSet<String> visited, ArrayList<String>localPathList,List<List<String>> allPaths){
		if(start.equals(end)){
			allPaths.add((List<String>) localPathList.clone());
		}
		visited.add(start);

		for(Edge adjacent : railwayNetwork.get(start)){
				if(!visited.contains(adjacent.getDestination())){
						localPathList.add(adjacent.getDestination());
						findAllPathsUtils(adjacent.getDestination(), end, visited, localPathList,allPaths);
						localPathList.remove(adjacent.getDestination());
				}
		}
	}*/

	private StationsData() {
		addStations();
	}
	public static StationsData getInstance() {
		return STATION_DB;
	}
	private final List<Station> stations = new ArrayList<>();
	
	public Station addStation(String name, String code,int noOfPlatforms, StationType stype ) {
		Station station = new Station(name, code, noOfPlatforms, stype);
		stations.add(station);
		return station;
	}
	public List<Station> getStationListFromNameList(List<String> stationNames){
		List<Station> stations = new ArrayList<>();
		for(String stationName : stationNames){
			Station station =findStation(stationName);
			stations.add(station);
		}
		return stations;
	}
	public Station findStation(String sName) {
		Station station= null;
		Iterator<Station> stationIterator = stations.iterator(); 
		while (stationIterator.hasNext()) {
			station = stationIterator.next();
			if(station.getName().equalsIgnoreCase(sName)) {
				break;
			}
		}
		return station;
	}
	// dummy initialization
	private void addStations() {
		int count  = 26;
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
	public List<Station> getAllStations(){

		return stations;
	}
}
