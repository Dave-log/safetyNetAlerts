package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.AgeGroupCount;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class FireStationService implements EntityService<FireStation> {

    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    @Autowired
    private final FireStationRepository fireStationRepository;
    @Autowired
    private final EmergencyService emergencyService;

    public FireStationService(FireStationRepository fireStationRepository, EmergencyService emergencyService) {
        this.fireStationRepository = fireStationRepository;
        this.emergencyService = emergencyService;
    }

    @Override
    public Collection<FireStation> findAll() {
        return fireStationRepository.findAll();
    }

    @Override
    public void create(FireStation fireStation) {
        fireStationRepository.save(fireStation);
    }

    public void creates(List<FireStation> listFireStations) {
        fireStationRepository.saveAll(listFireStations);
    }

    public FireStation find(int stationNumber) {
        return fireStationRepository.find(stationNumber);
    }

    public FireStationDTO findPersonsCoveredByStation(Integer stationNumber) {
        List<Person> personsCoveredByStation = emergencyService.findPersonsCoveredByStation(stationNumber);
        AgeGroupCount ageGroupCount = AgeGroupCounter.countAgeGroups(personsCoveredByStation);
        int numberOfAdults = ageGroupCount.getAdults();
        int numberOfChildren = ageGroupCount.getChildren();

        return new FireStationDTO(personsCoveredByStation, numberOfAdults, numberOfChildren);
    }

    @Override
    public FireStation update(FireStation fireStation) {

        FireStation existingFireStation = null;
        FireStation optionalFireStation = find(fireStation.getStationNumber());
        if (optionalFireStation != null) {
            existingFireStation = optionalFireStation;
            fireStationRepository.save(existingFireStation);
            logger.info("The addresses of firestation number " + existingFireStation.getStationNumber() + " has been updated.");
        } else {
            logger.error("The firestation does not exist. ");
        }
        return existingFireStation;
    }

    public void deleteByAddress(Integer stationNumber, String address) {

        fireStationRepository.deleteByAddress(stationNumber, address);
    }

    public void deleteByStation(Integer stationNumber) {

        fireStationRepository.deleteByStation(stationNumber);
    }

    @Override
    public FireStation delete() { return null; }
}
