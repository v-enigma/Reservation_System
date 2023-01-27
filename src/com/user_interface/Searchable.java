package com.user_interface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.database.BookingsData;
import com.database.ScheduleData;
import com.database.StationsData;
import com.database.TrainsData;
import com.reservation_system.*;

public interface Searchable {
    default void pnrSearch() {
        System.out.println(PrintStatements.PNR_SEARCH);
        Long pnr = Helper.getLongInput();
        //BookingFactory.getInstance().
    }

    default List<Object> getJourneyDetails() {
        System.out.println("Enter the source station name");
        String source = Helper.getLineInput();
        System.out.println("Enter the destination station name");
        String destination = Helper.getLineInput();
        System.out.println("Enter date of journey.Enter the date in YYYY-MM-DD format");
        LocalDate dateOfJourney = Helper.getJourneyDate();

        //System.out.println(dateOfJourney);
        List<Object> objects = new ArrayList<>();
        objects.add(source);
        objects.add(destination);
        objects.add(dateOfJourney);
        return objects;
    }

    default void search() {
        System.out.println("Enter source, destination station  and  date of journey\n ");
        List<Object> objects = getJourneyDetails();
        searchTrain(objects);

    }

    default List<Train> searchTrain(List<Object> objects) {
        //System.out.println("I entered into");


        String source = objects.get(0).toString();
        String destination = objects.get(1).toString();
        Station sStation = StationsData.getInstance().findStation(source);// has to update
        Station dStation = StationsData.getInstance().findStation(destination); // has to update
        //System.out.println("Enter date of journey.Enter the date in DD/MM/YYYY format");
        //String date = "";
        LocalDate dateOfJourney = (LocalDate) objects.get(2);
        List<Train> trains = new ArrayList<>();
        //System.out.println("I entered into");
        if (sStation == null || dStation == null || source.equalsIgnoreCase(destination))
            if (sStation == null)
                System.out.println("Invalid source station");
            else
                System.out.println("Invalid destination station");

        else {

            List<Integer> trainNumbers = ScheduleHelper.getInstance().searchTrainsBetweenStations(sStation, dStation, dateOfJourney);// has to look for the
            if (trainNumbers.size() == 0)
                System.out.println(PrintStatements.NO_TRAINS);
            int count = 0;
            for (int trainNumber : trainNumbers) {
                count++;
                Train train = TrainsData.getInstance().findTrainById(trainNumber); // has to update
                System.out.println(PrintStatements.TOP_HEADING);
                trainDetails(count, train);
                trains.add(train);

            }
            //System.out.println("I entered into");
        }
        return trains;
    }

    default void trainDetails(int index, Train train) {
        System.out.println(index + "  " + train.getId() + "  " + train.getName());


    }

    default List<Integer> findTrains(int trainNo) {
        if (!TrainFactory.getInstance().validateTrain(trainNo)) {
            return null;
        }
        List<Integer> trainsRegIds = TrainFactory.getInstance().getTrainsRegIds(trainNo);
        return trainsRegIds;
    }

}
