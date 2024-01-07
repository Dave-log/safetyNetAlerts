package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.controller.FireStationController;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository implements EntityRepository<FireStation> {

    private final Map<String, Set<String>> fireStationMap = new HashMap<>();

    public FireStation find(String station) {
        Set<String> addresses = fireStationMap.get(station);
        if (addresses != null) {
            return new FireStation(station, addresses);
        }
        return null;
    }

    @Override
    public void save(FireStation fireStation) {
        String station = fireStation.getStation();
        Set<String> addresses = fireStation.getAddresses();

        if (fireStationMap.containsKey(station)) {
            fireStationMap.get(station).addAll(addresses);
        } else {
            Set<String> newAddresses = new HashSet<>(addresses);
            fireStationMap.put(station, newAddresses);
        }
    }

    public void deleteByAddress(String station, String address) {
        if (fireStationMap.containsKey(station)) {
            Set<String> addresses = fireStationMap.get(station);

            if (addresses.remove(address) && addresses.isEmpty()) {
                fireStationMap.remove(station);
            }
        }
    }

    public void deleteByStation(String station) {
        fireStationMap.remove(station);
    }

    @Override
    public void saveAll(Collection<FireStation> list) {
        for (FireStation fireStation : list) {
            save(fireStation);
        }
        System.out.println("Taille de la fireStationMap : " + fireStationMap.size());
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
