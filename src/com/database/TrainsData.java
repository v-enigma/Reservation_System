package com.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reservation_system.Train;

public class TrainsData {
	private static int helperId =1;
	private final static TrainsData TRAIN_DB = new TrainsData();
	private final Map<Integer, Train>allTrains = new HashMap<>();
	private final Map<Integer, List<Integer>>idToRegIdMap = new HashMap<>();
	public static TrainsData getInstance() {
		
		return TRAIN_DB;
	}
	private TrainsData() {
		
	}
	public static int  getHelperId() {
		return ++helperId;
		
	}
	public void addTrain(Train train) {
		
		allTrains.put(train.getRegId(), train);
		if(train.getId()>0  &&   idToRegIdMap.containsKey(train.getId())) {
			idToRegIdMap.get(train.getId()).add(train.getRegId());
		}
	}
	public List<Integer> getAllTrainRegIdsMappedToTrainNo(int trainNo){
		if(idToRegIdMap.containsKey(trainNo)){
			return idToRegIdMap.get(trainNo);
		}
		return null;
	}
	public Train getTrainByRegId(int regId){
		return allTrains.get(regId);
	}
	public Train findTrainById(int trainId) {
		Train train = null;
		if(idToRegIdMap.containsKey(trainId)    &&    idToRegIdMap.get(trainId).size()>0 ) {
			int trainRegId = idToRegIdMap.get(trainId).get(0);
			train = allTrains.get(trainRegId);
		}
		return train;
	}
}
