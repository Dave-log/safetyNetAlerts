package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.AgeGroupCount;
import com.safetyNet.safetyNetAlerts.model.Person;

import java.util.List;

public class AgeGroupCounter {

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

        return new AgeGroupCount(adults, children);
    }
}
