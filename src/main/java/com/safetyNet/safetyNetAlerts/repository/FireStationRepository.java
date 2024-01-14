package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.FireStation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FireStationRepository {

    Map<Integer, Set<String>> fireStationMap = new HashMap<>();

    FireStation find(Integer StationNumber);

    List<FireStation> findAll();

    void save(FireStation fireStation);

    void saveAll(List<FireStation> fireStationList);

    void update(String address, Integer newStationNumber);

    void deleteAddress(Integer stationNumber, String address);

    void deleteFireStation(Integer stationNumber);
}
