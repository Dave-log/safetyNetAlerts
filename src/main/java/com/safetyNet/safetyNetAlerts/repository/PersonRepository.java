package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.Person;
import java.util.List;

public interface PersonRepository {

    Person find(String firstName, String lastName);

    List<Person> findByAddress(String address);

    List<Person> findByCity(String city);

    List<Person> findAll();

    void save(Person person);

    void saveAll(List<Person> personList);

    void update(Person person);

    void delete(Person person);
}
