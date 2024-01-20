package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneAlertService {

    @Autowired
    private FireStationService fireStationService;

    public List<String> getPhoneAlert(Integer stationNumber) {
        List<String> phoneNumbers = new ArrayList<>();
        List<Person> personsCoveredByStation = fireStationService.findPersonsCoveredByStation(stationNumber);

        for (Person person : personsCoveredByStation) {
            phoneNumbers.add(person.getPhone());
        }

        return phoneNumbers;
    }
}
