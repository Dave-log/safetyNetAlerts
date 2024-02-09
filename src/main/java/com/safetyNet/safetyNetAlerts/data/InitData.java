package com.safetyNet.safetyNetAlerts.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.safetyNetAlerts.exceptions.MedicalRecordNotFoundException;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.impl.FireStationRepositoryImpl;
import com.safetyNet.safetyNetAlerts.repository.impl.MedicalRecordRepositoryImpl;
import com.safetyNet.safetyNetAlerts.repository.impl.PersonRepositoryImpl;
import com.safetyNet.safetyNetAlerts.utils.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class InitData {

    private static final Logger logger = LogManager.getLogger(InitData.class);

    @Autowired
    private PersonRepositoryImpl personRepository;
    @Autowired
    private FireStationRepositoryImpl fireStationRepository;
    @Autowired
    private MedicalRecordRepositoryImpl medicalRecordRepository;

    @Bean
    public CommandLineRunner initDataFromJsonFile() {
        return args -> {
            JsonReader jsonReader = new JsonReader(new ObjectMapper());
            String jsonFilePath = "src/main/resources/data.json";
            try {
                medicalRecordRepository.saveAll(jsonReader.readListFromFile(jsonFilePath, "medicalrecords", MedicalRecord.class));
                personRepository.saveAll(jsonReader.readListFromFile(jsonFilePath, "persons", Person.class));
                fireStationRepository.saveAll(jsonReader.readListFromFile(jsonFilePath, "firestations", FireStation.class));

                for (Person person : personRepository.findAll()) {
                    attributeMedicalRecordToPerson(person);
                    calculateAgeAndAssignToPerson(person);
                }
                for (FireStation fireStation : fireStationRepository.findAll()) {
                    recordPersonsCoveredByStation(fireStation);
                }

                logger.info("Data successfully read from json file from " + jsonFilePath);
            } catch (Exception e) {
                logger.error("Unable to read from json file " + e.getMessage());
            }
        };
    }

    private void attributeMedicalRecordToPerson(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        MedicalRecord matchingMedicalRecord = medicalRecordRepository.find(firstName, lastName);

        if (matchingMedicalRecord == null) {
            throw new MedicalRecordNotFoundException(firstName, lastName);
        }
        person.setMedicalRecord(matchingMedicalRecord);
    }

    private void calculateAgeAndAssignToPerson(Person person) {
        String birthdate = person.getMedicalRecord().getBirthdate();
        person.setAge(AgeCalculator.calculate(birthdate));
    }

    private void recordPersonsCoveredByStation(FireStation fireStation) {
        List<Person> personsCovered = new ArrayList<>();
        Set<String> addresses = fireStation.getAddresses();

        for (String address : addresses) {
            List<Person> personsByAddress = personRepository.findByAddress(address);
            personsCovered.addAll(personsByAddress);
        }

        fireStation.setPersonsCovered(personsCovered);
    }

}
