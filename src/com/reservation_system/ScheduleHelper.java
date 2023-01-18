package com.reservation_system;

import com.database.ScheduleData;
import com.database.TrainsData;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScheduleHelper {
    private static final ScheduleHelper SCHEDULE_HELPER = new ScheduleHelper();
    private ScheduleHelper(){

    }
    public static ScheduleHelper getInstance(){
        return SCHEDULE_HELPER;
    }
    private List<LocalTime> calculateDepartureTimeOfALlStations(List<LocalTime> arrivalTimes , LocalTime sourceDepartureTime, Iterator<Station> allStations, List<Boolean> isStop){
        List<LocalTime> departureTimeFromSourceToDestination = new ArrayList<>();

        int index =0;
        while(allStations.hasNext()){
            Station station = allStations.next();
            if(index == 0) {
                departureTimeFromSourceToDestination.add(sourceDepartureTime);
                index++;
                continue;
            }
            if(isStop.get(index) ){
               LocalTime time = arrivalTimes.get(index);
               int duration = station.getStype().getDuration();
               time = time.plusMinutes(duration);
               departureTimeFromSourceToDestination.add(time);

            }
            else{
                departureTimeFromSourceToDestination.add(arrivalTimes.get(index));
            }
            index++;
        }
        return departureTimeFromSourceToDestination;
    }
    public void createSchedule(int id, int trainId , List<LocalTime> arrivalTime, LocalTime sourceDepartureTime, List<Boolean> isStop, List<List<DayOfWeek>> scheduledDays){
        Route route = TrainsData.getInstance().getTrainByRegId(id).getRoute();
        Iterator<Station>allStations = route.getAllStations();
        List<LocalTime> departureTime = calculateDepartureTimeOfALlStations(arrivalTime,sourceDepartureTime,allStations,isStop );
        Schedule schedule = new Schedule(id, trainId,route,arrivalTime,departureTime,isStop, scheduledDays);
        ScheduleData.getInstance().addSchedule(schedule);
        TrainsData.getInstance().getTrainByRegId(id).setSchedule(schedule);

    }
}
