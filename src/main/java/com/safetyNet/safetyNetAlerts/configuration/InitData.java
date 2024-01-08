package com.safetyNet.safetyNetAlerts.configuration;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import com.safetyNet.safetyNetAlerts.service.AgeCalculator;
import com.safetyNet.safetyNetAlerts.utils.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;

@Configuration
public class InitData {

    private static final Logger logger = LogManager.getLogger(InitData.class);

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Bean
    public CommandLineRunner initDataFromJsonFile() {
        return args -> {
            JsonReader jsonReader = new JsonReader();
            String jsonFilePath = "src/main/resources/data.json";
            try {
                personRepository.saveAll(jsonReader.readListFromFile(jsonFilePath, "persons", Person.class));
                fireStationRepository.saveAll(jsonReader.readListFromFile(jsonFilePath, "firestations", FireStation.class));
                medicalRecordRepository.saveAll(jsonReader.readListFromFile(jsonFilePath, "medicalrecords", MedicalRecord.class));

                // Calculate ages and assign to persons
                Collection<Person> allPersons = personRepository.findAll();
                for (Person person : allPersons) {
                    String birthdate = medicalRecordRepository.find(person.getFirstName(), person.getLastName()).getBirthdate();
                    if (birthdate != null) {
                        person.setAge(AgeCalculator.calculate(birthdate));
                    }
                }
                personRepository.saveAll(allPersons);

                logger.info("Data successfully read from json file from " + jsonFilePath);
            } catch (Exception e) {
                logger.error("Unable to read from json file " + e.getMessage());
            }
        };
    }

}
