package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.AgeGroupCount;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgeGroupCounterTest {

    @Test
    public void testCountAgeGroups_EmptyList() {
        List<Person> emptyList = new ArrayList<>();

        AgeGroupCount result = AgeGroupCounter.countAgeGroups(emptyList);

        assertEquals(0, result.getAdults());
        assertEquals(0, result.getChildren());
    }

    @Test
    public void testCountAgeGroups_NoChild() {
        List<Person> adultList = new ArrayList<>();
        adultList.add(Person.builder().firstName("John").lastName("Doe").age(30).build());
        adultList.add(Person.builder().firstName("Jane").lastName("Doe").age(32).build());

        AgeGroupCount result = AgeGroupCounter.countAgeGroups(adultList);

        assertEquals(0, result.getChildren());
        assertEquals(2, result.getAdults());
    }

    @Test
    public void testCountAgeGroups_NoAdult() {
        List<Person> childList = new ArrayList<>();
        childList.add(Person.builder().firstName("John").lastName("Doe").age(10).build());
        childList.add(Person.builder().firstName("Jane").lastName("Doe").age(8).build());

        AgeGroupCount result = AgeGroupCounter.countAgeGroups(childList);

        assertEquals(2, result.getChildren());
        assertEquals(0, result.getAdults());
    }

    @Test
    public void testCountAgeGroups_MixedAgeGroups() {
        List<Person> personList = new ArrayList<>();
        personList.add(Person.builder().firstName("John").lastName("Doe").age(30).build());
        personList.add(Person.builder().firstName("Jane").lastName("Doe").age(32).build());
        personList.add(Person.builder().firstName("Jack").lastName("Doe").age(8).build());

        AgeGroupCount result = AgeGroupCounter.countAgeGroups(personList);

        assertEquals(1, result.getChildren());
        assertEquals(2, result.getAdults());
    }
}
