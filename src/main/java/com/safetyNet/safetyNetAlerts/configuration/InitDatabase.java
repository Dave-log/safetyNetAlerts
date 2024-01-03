package com.safetyNet.safetyNetAlerts.configuration;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.FireStationService;
import com.safetyNet.safetyNetAlerts.service.MedicalRecordService;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import com.safetyNet.safetyNetAlerts.utils.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
public class InitDatabase {

    private static final Logger logger = LogManager.getLogger(InitDatabase.class);

    @Bean
    CommandLineRunner initDatabaseFromJsonFile(PersonService personService, FireStationService fireStationService, MedicalRecordService medicalRecordService) {
        return args -> {
            JsonReader jsonReader = new JsonReader();
            String jsonFilePath = "src/main/resources/data.json";
            try {
                List<Person> listPerson = jsonReader.readListFromFile(jsonFilePath, "persons", Person.class);
                List<FireStation> listFireStation = jsonReader.readListFromFile(jsonFilePath, "firestations", FireStation.class);
                List<MedicalRecord> listMedicalRecord = jsonReader.readListFromFile(jsonFilePath, "medicalrecords", MedicalRecord.class);
                personService.createPersons(listPerson);
                fireStationService.createFireStations(listFireStation);
                medicalRecordService.createMedicalRecords(listMedicalRecord);
                logger.info("Entities successfully saved to database!");
            } catch (IOException e) {
                logger.error("Unable to save persons to database: " + e.getMessage());
            }
        };
    }

    /*
    @Bean
    CommandLineRunner removeDuplicatesOnStartup(FireStationService fireStationService) {
        return args -> {
            fireStationService.removeDuplicateFireStations();
            logger.info("Duplicates removed!");
        };
    }
*/
}
