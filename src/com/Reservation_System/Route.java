package com.Reservation_System;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Route {
	
	private final Map<Station, Integer> routeMap = new LinkedHashMap<>();
	
	public void addStationWithDistance(Station station, int distance) {
		routeMap.put(station, distance);
	}
	
	public int getDistance(Station station) {
		return routeMap.get(station);
	}
	
	public boolean hasStop(Station station) {
		return routeMap.containsKey(station);
	}
	public Iterator<Station> getAllStops(){
		Set<Station> stops = routeMap.keySet();
		return stops.iterator();
		
	}
	public List<String> getStationCodesInRoute() {
		List<String> sCodes = new ArrayList<>();
		Set<Station> stations= routeMap.keySet();
		Iterator<Station> stationsIterator = stations.iterator();
		while(stationsIterator.hasNext()) {
			Station temp = stationsIterator.next();
			String Id = temp.getId();
			sCodes.add(Id);
		}
		return sCodes;
	}
	public int getStopsCount() {
		//System.out.println("Stops count "+ routeMap.size());
		return routeMap.size();
	}
}
