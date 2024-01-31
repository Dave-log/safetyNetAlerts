package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.AgeGroupCount;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class AgeGroupCounter {

    private static final Logger logger = LogManager.getLogger(AgeGroupCounter.class);

    private AgeGroupCounter() {}

    public static AgeGroupCount countAgeGroups(List<Person> listPersons) {
        int adults = 0;
        int children = 0;

        for (Person person : listPersons) {
            if (person.getAge() >= 18) {
                adults++;
            } else {
                children++;
            }
        }

        logger.debug("Result of counting adults and children : adults: " + adults + ", children: " + children);
        return new AgeGroupCount(adults, children);
    }
}
