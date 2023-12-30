package com.safetyNet.safetyNetAlerts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import com.safetyNet.safetyNetAlerts.utils.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Hello World!");
	}

	@Bean
	CommandLineRunner initDatabaseFromJsonFile(PersonService personService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			JsonReader jsonReader = new JsonReader();
			String jsonFilePath = "src/main/resources/data.json";
			try {
				List<Person> listPerson = jsonReader.readListFromFile(jsonFilePath, "persons", Person.class);
				personService.saveAll(listPerson);
				logger.info("Persons Saved in database!");
			} catch (IOException e) {
				logger.error("Unable to save persons to database: " + e.getMessage());
			}
		};
	}
}
