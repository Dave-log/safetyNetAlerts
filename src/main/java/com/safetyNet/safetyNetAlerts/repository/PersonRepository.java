package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.Person;
import java.util.List;

public interface PersonRepository {
    List<Person> getAllPersons();
    Person getPerson(String firstName, String lastName);
    List<Person> getPersonFromAddress(String address, String zip, String city);
    void addPerson(Person person);
    Person updatePerson(Person person);
    Person deletePerson(Person person);
}
