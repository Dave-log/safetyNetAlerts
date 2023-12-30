package com.safetyNet.safetyNetAlerts;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {}

	@Bean
	CommandLineRunner initDatabaseFromJsonFile(PersonService personService, FireStationService fireStationService, MedicalRecordService medicalRecordService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			JsonReader jsonReader = new JsonReader();
			String jsonFilePath = "src/main/resources/data.json";
			try {
				List<Person> listPerson = jsonReader.readListFromFile(jsonFilePath, "persons", Person.class);
				List<FireStation> listFireStation = jsonReader.readListFromFile(jsonFilePath, "firestations", FireStation.class);
				List<MedicalRecord> listMedicalRecord = jsonReader.readListFromFile(jsonFilePath, "medicalrecords", MedicalRecord.class);
				personService.saveAll(listPerson);
				fireStationService.saveAll(listFireStation);
				medicalRecordService.saveAll(listMedicalRecord);
				logger.info("Entities saved to database!");
			} catch (IOException e) {
				logger.error("Unable to save persons to database: " + e.getMessage());
			}
		};
	}
}
