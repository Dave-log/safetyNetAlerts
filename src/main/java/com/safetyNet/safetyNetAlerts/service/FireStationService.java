package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    @Autowired
    private FireStationRepository fireStationRepository;

    public FireStationService(FireStationRepository fireStationRepository) { this.fireStationRepository = fireStationRepository; }

    public Iterable<FireStation> getFireStations() {
        return fireStationRepository.findAll();
    }

    public FireStation createFireStation(FireStation fireStation) {
        return fireStationRepository.save(fireStation);
    }

    public List<FireStation> createFireStations(List<FireStation> listFireStations) {
        return fireStationRepository.saveAll(listFireStations);
    }

    public FireStation getFireStationByAddress(String address) {
        return fireStationRepository.findByAddress(address).orElse(null);
    }

    public List<FireStation> getFireStationByStation(String station) {
        return fireStationRepository.findByStation(station).orElse(null);
    }

    public FireStation updateFireStation(FireStation fireStation) {

        FireStation existingFireStation = null;
        FireStation optionalFireStation = getFireStationByAddress(fireStation.getAddress());
        if (optionalFireStation != null) {
            existingFireStation = optionalFireStation;
            existingFireStation.setStation(fireStation.getStation());
            fireStationRepository.save(existingFireStation);
            logger.info("The firestation number at " + fireStation.getAddress() + " has been updated.");
        } else {
            createFireStation(fireStation);
            logger.warn("The firestation does not exist and has been added to the database.");
        }
        return existingFireStation;
    }

    public FireStation deleteFireStationByAddress(String address) {

        FireStation existingFireStation = null;
        FireStation optionalFireStation = getFireStationByAddress(address);
        if (optionalFireStation != null) {
            existingFireStation = optionalFireStation;
            fireStationRepository.delete(existingFireStation);
            logger.info("The firestation at " + address + " has been deleted from database.");
        } else {
            logger.warn("The firestation at " + address + " does not exist.");
        }
        return existingFireStation;
    }

    public List<FireStation> deleteFireStationByStation(String station) {

        List<FireStation> existingFireStations = null;
        List<FireStation> optionalFireStations = getFireStationByStation(station);
        if (optionalFireStations != null) {
            existingFireStations = new ArrayList<>(optionalFireStations);
            fireStationRepository.deleteAll(existingFireStations);
            logger.info("The firestation n°" + station + " has been deleted from database.");
        } else {
            logger.warn("The firestation n°" + station + " does not exist.");
        }
        return existingFireStations;
    }

    /*
    @Transactional
    public void removeDuplicateFireStations() {
        fireStationRepository.deleteDuplicates();
        List<FireStation> listFireStations = fireStationRepository.findAll();
        Map<String, List<FireStation>> mapFireStations = listFireStations.stream()
                .collect(Collectors.groupingBy(fireStation -> fireStation.getAddress() + "-" + fireStation.getStation()));

        mapFireStations.forEach((key, value) -> {
            if (value.size() > 1) {
                List<String> duplicateAddresses = value.stream()
                        .skip(1)
                        .map(fireStation -> fireStation.getAddress() + "-" + fireStation.getStation())
                        .collect(Collectors.toList());

                fireStationRepository.deleteDuplicates(duplicateAddresses);
            }
        });
        */
    //}
}
