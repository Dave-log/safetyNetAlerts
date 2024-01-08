package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository implements EntityRepository<FireStation> {

    private final static Logger logger = LogManager.getLogger(FireStationRepository.class);

    private final Map<Integer, Set<String>> fireStationMap = new HashMap<>();

    public FireStation find(Integer stationNumber) {
        Set<String> addresses = fireStationMap.get(stationNumber);
        if (addresses != null) {
            return new FireStation(stationNumber, addresses);
        }
        return null;
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

    public void deleteByAddress(Integer stationNumber, String address) {
        if (fireStationMap.containsKey(stationNumber)) {
            Set<String> addresses = fireStationMap.get(stationNumber);

            if (addresses.remove(address) && addresses.isEmpty()) {
                fireStationMap.remove(stationNumber);
            }
        }
    }

    public void deleteByStation(Integer stationNumber) {
        fireStationMap.remove(stationNumber);
    }

    @Override
    public void saveAll(Collection<FireStation> list) {
        for (FireStation fireStation : list) {
            save(fireStation);
        }
    }

    @Override
    public Collection<FireStation> findAll() {
       return fireStationMap.entrySet().stream()
               .map(entry -> new FireStation(entry.getKey(), new HashSet<>(entry.getValue())))
               .collect(Collectors.toList());
    }

    @Override
    public FireStation find() { return null; }

    @Override
    public void delete() {}

}
