package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.FireStation;

import java.util.List;

public interface FireStationService {

    FireStation findByStationNumber(Integer stationNumber);

    FireStationDTO findPersonsCoveredByStation(Integer stationNumber);

    List<FireStation> findAll();

    void create(FireStation fireStation);

    void creates(List<FireStation> fireStationList);

    void update(String address, Integer newStationNumber);

    void deleteAddress(Integer stationNumber, String address);

    void deleteFireStation(Integer stationNumber);

}
