package com.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reservation_system.Train;

public final class TrainsData {
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
	public boolean addTrain(Train train) {
		boolean hasAdded = false;
		if(allTrains.put(train.getRegId(), train) == null)
			hasAdded = true;
		if(!idToRegIdMap.containsKey(train.getId())) {
			idToRegIdMap.put(train.getId(), new ArrayList<>());
		}
		hasAdded = hasAdded && idToRegIdMap.get(train.getId()).add(train.getRegId());
		return hasAdded;
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
	public boolean removeTrain(int regId, int trainNo){
		if(allTrains.containsKey(regId) && idToRegIdMap.containsKey(trainNo)){
			allTrains.remove(regId);
			idToRegIdMap.get(trainNo).remove((Object) regId);
			return true;
		}
		return false;
	}
}
