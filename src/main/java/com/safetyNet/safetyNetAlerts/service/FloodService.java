package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FloodDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FloodService {
    @Autowired
    FireStationService fireStationService;

    public FloodDTO getFloodInfoByStations(List<Integer> stationNumberList) {
        Map<String, List<ResidentDTO>> residentsByAddress = new HashMap<>();

        for (Integer stationNumber : stationNumberList) {
            List<Person> personsCoveredByStation = fireStationService.findPersonsCoveredByStation(stationNumber);

            for (Person person : personsCoveredByStation) {
                String address = person.getAddress();
                ResidentDTO residentDTO = new ResidentDTO(person);
                residentsByAddress.computeIfAbsent(address, k -> new ArrayList<>()).add(residentDTO);
            }
        }

        return new FloodDTO(residentsByAddress);
    }
}
