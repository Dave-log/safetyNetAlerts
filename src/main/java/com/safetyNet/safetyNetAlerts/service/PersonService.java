package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.ChildAlertDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.Person;

import java.util.List;
import java.util.Set;

public interface PersonService {

    Person findByFullName(String firstName, String lastName);

    List<Person> findByAddress(String address);

    List<Person> findByCity(String city);

    List<ChildAlertDTO> findChildrenByAddress(String address);

    List<Person> findAll();

    Set<String> getCommunityEmails(String city);

    void create(Person person);

    void creates(List<Person> personList);

    void update(Person person);

    void delete(String firstName, String lastName);

    ResidentDTO getPersonInfo(String firstName, String lastName);

}
