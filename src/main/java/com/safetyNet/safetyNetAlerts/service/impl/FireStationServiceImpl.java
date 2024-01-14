package com.safetyNet.safetyNetAlerts.service.impl;

import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.AgeGroupCount;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import com.safetyNet.safetyNetAlerts.service.AgeGroupCounter;
import com.safetyNet.safetyNetAlerts.service.EmergencyService;
import com.safetyNet.safetyNetAlerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {

    private static final Logger logger = LogManager.getLogger(FireStationServiceImpl.class);

    @Autowired
    private final FireStationRepository fireStationRepository;
    @Autowired
    private final EmergencyService emergencyService;

    public FireStationServiceImpl(FireStationRepository fireStationRepository, EmergencyService emergencyService) {
        this.fireStationRepository = fireStationRepository;
        this.emergencyService = emergencyService;
    }

    @Override
    public FireStation findByStationNumber(Integer stationNumber) {
        return fireStationRepository.find(stationNumber);
    }

    @Override
    public FireStationDTO findPersonsCoveredByStation(Integer stationNumber) {
        List<Person> personsCoveredByStation = emergencyService.findPersonsCoveredByStation(stationNumber);
        AgeGroupCount ageGroupCount = AgeGroupCounter.countAgeGroups(personsCoveredByStation);
        int numberOfAdults = ageGroupCount.getAdults();
        int numberOfChildren = ageGroupCount.getChildren();

        return new FireStationDTO(personsCoveredByStation, numberOfAdults, numberOfChildren);
    }

    @Override
    public List<FireStation> findAll() {
        return fireStationRepository.findAll();
    }

    @Override
    public void create(FireStation fireStation) {
        fireStationRepository.save(fireStation);
    }

    @Override
    public void creates(List<FireStation> fireStationList) {
        fireStationRepository.saveAll(fireStationList);
    }

    @Override
    public void update(String address, Integer newStationNumber) {
        fireStationRepository.update(address, newStationNumber);
    }

    @Override
    public void deleteAddress(Integer stationNumber, String address) {
        fireStationRepository.deleteAddress(stationNumber, address);
    }

    @Override
    public void deleteFireStation(Integer stationNumber) {
        fireStationRepository.deleteFireStation(stationNumber);
    }

}
