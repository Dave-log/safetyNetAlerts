package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmergencyService {

    private static final Logger logger = LogManager.getLogger(EmergencyService.class);

    @Autowired
    private final PersonRepository personRepository;
    @Autowired
    private final FireStationRepository fireStationRepository;


    public EmergencyService(PersonRepository personRepository, FireStationRepository fireStationRepository) {
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
    }

    public List<Person> findPersonsCoveredByStation(Integer stationNumber) {
        List<Person> personsCoveredByStation = new ArrayList<>();
        Set<String> addressesCoveredByStation = fireStationRepository.find(stationNumber).getAddresses();

        for (Person person : personRepository.findAll()) {
            if (addressesCoveredByStation.contains(person.getAddress())) {
                personsCoveredByStation.add(person);
            }
        }

        return personsCoveredByStation;
    }

}
