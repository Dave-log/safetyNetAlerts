package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.FireStation;
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

    public FireStationService(FireStationRepository fireStationRepository) { this.fireStationRepository = fireStationRepository; }

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

    public FireStation find(String station) {
        return fireStationRepository.find(station);
    }

    @Override
    public FireStation update(FireStation fireStation) {

        FireStation existingFireStation = null;
        FireStation optionalFireStation = find(fireStation.getStation());
        if (optionalFireStation != null) {
            existingFireStation = optionalFireStation;
            fireStationRepository.save(existingFireStation);
            logger.info("The addresses of firestation number " + existingFireStation.getStation() + " has been updated.");
        } else {
            logger.error("The firestation does not exist. ");
        }
        return existingFireStation;
    }

    public void deleteByAddress(String station, String address) {

        fireStationRepository.deleteByAddress(station, address);
    }

    public void deleteByStation(String station) {

        fireStationRepository.deleteByStation(station);
    }

    @Override
    public FireStation delete() { return null; }
}
