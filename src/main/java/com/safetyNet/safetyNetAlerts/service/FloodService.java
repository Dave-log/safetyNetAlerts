package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FloodDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
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
    MedicalRecordService medicalRecordService;

    @Autowired
    EmergencyService emergencyService;

    public FloodDTO getFloodInfoByStations(List<Integer> stationNumberList) {
        Map<String, List<ResidentDTO>> residentsByAddress = new HashMap<>();

        for (Integer stationNumber : stationNumberList) {
            List<Person> personsCoveredByStation = emergencyService.findPersonsCoveredByStation(stationNumber);

            for (Person person : personsCoveredByStation) {
                String address = person.getAddress();
                MedicalRecord medicalRecord = medicalRecordService.find(person.getFirstName(), person.getLastName());

                ResidentDTO residentDTO = new ResidentDTO(person, medicalRecord);
                residentsByAddress.computeIfAbsent(address, k -> new ArrayList<>()).add(residentDTO);
            }
        }

        return new FloodDTO(residentsByAddress);
    }
}
