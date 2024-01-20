package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.Person;

import java.util.List;

public interface FireStationService {

    FireStation findByStationNumber(Integer stationNumber);

    List<Integer> findByAddress(String address);

    List<FireStation> findAll();

    List<Person> findPersonsCoveredByStation(Integer stationNumber);

    FireStationDTO getPersonsCoveredByStationsSortedByAge(Integer stationNumber);

    void create(FireStation fireStation);

    void creates(List<FireStation> fireStationList);

    void update(String address, Integer newStationNumber);

    void deleteAddress(Integer stationNumber, String address);

    void deleteFireStation(Integer stationNumber);

}
