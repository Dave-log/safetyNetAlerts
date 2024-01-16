package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.AgeGroupCount;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
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
    private PersonRepository personRepository;
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

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

    public FireStationDTO getPersonsCoveredByStationsSortedByAge(Integer stationNumber) {
        List<Person> personsCoveredByStation = findPersonsCoveredByStation(stationNumber);
        AgeGroupCount ageGroupCount = AgeGroupCounter.countAgeGroups(personsCoveredByStation);
        int numberOfAdults = ageGroupCount.getAdults();
        int numberOfChildren = ageGroupCount.getChildren();

        return new FireStationDTO(personsCoveredByStation, numberOfAdults, numberOfChildren);
    }

    public ResidentDTO getPersonInfo(String firstName, String lastName) {
        Person matchingPerson = personRepository.find(firstName, lastName);
        MedicalRecord matchingMedicalRecord = medicalRecordRepository.find(firstName, lastName);

        return new ResidentDTO(matchingPerson, matchingMedicalRecord);
    }
}
