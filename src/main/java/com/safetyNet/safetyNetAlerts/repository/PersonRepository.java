package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.Person;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
