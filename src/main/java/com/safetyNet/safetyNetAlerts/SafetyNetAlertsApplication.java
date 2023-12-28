package com.safetyNet.safetyNetAlerts;

import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import com.safetyNet.safetyNetAlerts.repository.jsonRepository.JsonPersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Hello World!");
		PersonRepository pr = new JsonPersonRepository();
		logger.info(pr.getPerson("John", "Boyd"));
		logger.info(pr.getPersonFromAddress("892 Downing Ct", "97451", "Culver"));
	}
}
