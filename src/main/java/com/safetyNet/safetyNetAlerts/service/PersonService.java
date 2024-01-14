package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.ChildAlertDTO;
import com.safetyNet.safetyNetAlerts.model.Person;

import java.util.List;

public interface PersonService {

    Person findByFullName(String firstName, String lastName);

    List<Person> findByAddress(String address);

    List<ChildAlertDTO> findChildrenByAddress(String address);

    List<Person> findAll();

    void create(Person person);

    void creates(List<Person> personList);

    void update(Person person);

    void delete(String firstName, String lastName);
}
