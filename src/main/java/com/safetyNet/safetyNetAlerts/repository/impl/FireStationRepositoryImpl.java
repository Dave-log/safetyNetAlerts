package com.safetyNet.safetyNetAlerts.repository.impl;

import com.safetyNet.safetyNetAlerts.exceptions.StationNotFoundException;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {

    private final static Logger logger = LogManager.getLogger(FireStationRepositoryImpl.class);

    private final PersonRepository personRepository;
    private final Map<Integer, FireStation> fireStationMap;

    @Autowired
    public FireStationRepositoryImpl(Map<Integer, FireStation> fireStationMap, PersonRepository personRepository) {
        this.fireStationMap = fireStationMap;
        this.personRepository = personRepository;
    }

    public FireStation find(Integer stationNumber) {
        return fireStationMap.get(stationNumber);
    }

    @Override
    public List<FireStation> findAll() {
        return new ArrayList<>(fireStationMap.values());
    }

    @Override
    public void save(FireStation fireStation) {
        Integer stationNumber = fireStation.getStationNumber();

        if (fireStationMap.containsKey(stationNumber)) {
            FireStation existingFireStation = fireStationMap.get(stationNumber);

            existingFireStation.getAddresses().add(fireStation.getAddress());
        } else {
            Set<String> addresses = new HashSet<>();
            addresses.add(fireStation.getAddress());

            fireStation.setAddresses(addresses);
            fireStationMap.put(stationNumber, fireStation);
        }
    }

    @Override
    public void saveAll(List<FireStation> list) {
        for (FireStation fireStation : list) {
            save(fireStation);
        }
    }

    @Override
    public void update(String address, Integer newStationNumber) {
        // Remove the address from all existing fireStations except fireStation with newStationNumber as stationNumber
        fireStationMap.values().stream()
                .filter(station -> !station.getStationNumber().equals(newStationNumber))
                .forEach(station -> station.getAddresses().remove(address));

        if (fireStationMap.containsKey(newStationNumber)) {
            fireStationMap.get(newStationNumber).getAddresses().add(address);
        } else {
            throw new StationNotFoundException(newStationNumber);
        }

        logger.info("Firestation number has been updated for this address : " + address);
    }

    @Override
    public void deleteAddress(Integer stationNumber, String address) {
        if (fireStationMap.containsKey(stationNumber)) {
            FireStation fireStation = fireStationMap.get(stationNumber);

            if (fireStation.getAddresses().remove(address)) {
                logger.info("Address : " + address + " has been removed from firestation n°" + stationNumber);
            } else {
                logger.error("Address: " + address + " not found in firestation n°" + stationNumber);
            }

        } else {
            logger.error("Firestation n°" + stationNumber + " does not exist.");
        }
    }

    @Override
    public void deleteFireStation(Integer stationNumber) {
        if (fireStationMap.containsKey(stationNumber)) {
            fireStationMap.remove(stationNumber);
            logger.info("Firestation n°" + stationNumber + " has been deleted.");
        } else {
            logger.error("Firestation n°" + stationNumber + " does not exist.");
        }
    }

    @Override
    public List<Person> findPersonsCoveredByStation(Integer stationNumber) {
        List<Person> personsCoveredByStation = new ArrayList<>();
        Set<String> addressesCoveredByStation = find(stationNumber).getAddresses();

        for (Person person : personRepository.findAll()) {
            if (addressesCoveredByStation.contains(person.getAddress())) {
                personsCoveredByStation.add(person);
            }
        }

        return personsCoveredByStation;
    }
}
