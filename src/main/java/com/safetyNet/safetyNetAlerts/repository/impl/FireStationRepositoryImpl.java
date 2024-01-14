package com.safetyNet.safetyNetAlerts.repository.impl;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {

    private final static Logger logger = LogManager.getLogger(FireStationRepositoryImpl.class);

    public FireStation find(Integer stationNumber) {
        Set<String> addresses = fireStationMap.get(stationNumber);
        if (addresses != null) {
            return new FireStation(stationNumber, addresses);
        }
        return null;
    }

    @Override
    public List<FireStation> findAll() {
        return fireStationMap.entrySet().stream()
                .map(entry -> new FireStation(entry.getKey(), new HashSet<>(entry.getValue())))
                .collect(Collectors.toList());
    }

    @Override
    public void save(FireStation fireStation) {
        Integer stationNumber = fireStation.getStationNumber();
        String address = fireStation.getAddress();

        if (fireStationMap.containsKey(stationNumber)) {
            fireStationMap.get(stationNumber).add(address);
        } else {
            Set<String> newAddresses = new HashSet<>();
            newAddresses.add(address);
            fireStationMap.put(stationNumber, newAddresses);
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
        fireStationMap.values().forEach(stationAddresses -> stationAddresses.remove(address));
        fireStationMap.computeIfAbsent(newStationNumber, k -> new HashSet<>()).add(address);
        logger.info("Firestation number has been updated for this address : " + address);
    }

    @Override
    public void deleteAddress(Integer stationNumber, String address) {
        if (fireStationMap.containsKey(stationNumber)) {
            Set<String> addresses = fireStationMap.get(stationNumber);
            addresses.remove(address);;
            logger.info("Address : " + address + " has been removed from firestation n°" + stationNumber);
        } else {
            logger.error("Firestation n°" + stationNumber + "does not exist.");
        }
    }

    @Override
    public void deleteFireStation(Integer stationNumber) {
        if (fireStationMap.containsKey(stationNumber)) {
            fireStationMap.remove(stationNumber);
            logger.info("Firestation n°" + stationNumber + "has been deleted.");
        } else {
            logger.error("Firestation n°" + stationNumber + "does not exist.");
        }
    }
}
