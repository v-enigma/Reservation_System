package com.database;
import com.reservation_system.Route;
import com.reservation_system.Station;

import java.util.*;

public class NetworkStorage {
    private List<List<String>> allRoutes = new ArrayList<>();

    private final static NetworkStorage ROUTE_DATA = new NetworkStorage();
    private NetworkStorage(){
        generateRoutes();
    }
    public static NetworkStorage getInstance(){
        return ROUTE_DATA;
    }

    public void generateRoutes(){
        List<Station> stations = StationsData.getInstance().getAllStations();
        int count  = 1;
       List<String> adjacentStations = new ArrayList<>();
        for(Station station : stations){
            if(count%5 == 0){
                adjacentStations.add(station.getName());
                allRoutes.add(adjacentStations);
                System.out.println(adjacentStations);
                adjacentStations = new ArrayList<>();


            }
            else {
                adjacentStations.add(station.getName());
            }


            count++;
        }
    }
    public List<String> getAvailableRouteBetweenStations(String source, String destination){
        List<String> stationList = null;
        Iterator networkIterator = allRoutes.iterator();
        boolean reverse = false;
        while(networkIterator.hasNext()){

            List<String> stationsInRoute = (List<String>) networkIterator.next();
            if(stationsInRoute.contains(source) && stationsInRoute.contains(destination)){
               int sourceIndex =  stationsInRoute.indexOf(source);
               int destinationIndex = stationsInRoute.indexOf(destination);
               if(sourceIndex> destinationIndex){
                   reverse = true;
                   int temp = sourceIndex;
                   sourceIndex = destinationIndex;
                   destinationIndex = temp;
               }
                  stationList = stationsInRoute.subList(sourceIndex,destinationIndex+1);
                  if(reverse)
                      Collections.reverse(stationList);
                   return stationList;

            }
        }
        return stationList;
    }
}
