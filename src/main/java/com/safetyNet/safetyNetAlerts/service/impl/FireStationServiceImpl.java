package com.safetyNet.safetyNetAlerts.service.impl;

import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.AgeGroupCount;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import com.safetyNet.safetyNetAlerts.service.AgeGroupCounter;
import com.safetyNet.safetyNetAlerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {

    @Autowired
    private final FireStationRepository fireStationRepository;

    public FireStationServiceImpl(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    @Override
    public FireStation findByStationNumber(Integer stationNumber) {
        return fireStationRepository.find(stationNumber);
    }

    @Override
    public List<Integer> findByAddress(String address) {
        List<Integer> fireStationNumberList = new ArrayList<>();
        List<FireStation> fireStationList = fireStationRepository.findAll();

        for (FireStation fireStation : fireStationList) {
            if (fireStation.getAddresses().contains(address)) {
                fireStationNumberList.add(fireStation.getStationNumber());
            }
        }

        return fireStationNumberList;
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
    public void create(List<FireStation> fireStationList) {
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

    @Override
    public List<Person> findPersonsCoveredByStation(Integer stationNumber) {
        return fireStationRepository.findPersonsCoveredByStation(stationNumber);
    }

    @Override
    public FireStationDTO getPersonsCoveredByStationsSortedByAge(Integer stationNumber) {
        List<Person> personsCoveredByStation = findPersonsCoveredByStation(stationNumber);
        AgeGroupCount ageGroupCount = AgeGroupCounter.countAgeGroups(personsCoveredByStation);
        int numberOfAdults = ageGroupCount.getAdults();
        int numberOfChildren = ageGroupCount.getChildren();

        return new FireStationDTO(personsCoveredByStation, numberOfAdults, numberOfChildren);
    }
}
