package com.safetyNet.safetyNetAlerts.repository.impl;

import com.safetyNet.safetyNetAlerts.exceptions.MedicalRecordNotFoundException;
import com.safetyNet.safetyNetAlerts.exceptions.PersonNotFoundException;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import com.safetyNet.safetyNetAlerts.utils.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    private final Map<Pair<String, String>, Person> personMap;

    @Autowired
    public PersonRepositoryImpl(Map<Pair<String, String>, Person> personMap, MedicalRecordRepository medicalRecordRepository) {
        this.personMap = personMap;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public Person find(String firstName, String lastName) {
        return personMap.get(Pair.of(firstName, lastName));
    }

    @Override
    public List<Person> findByAddress(String address) {
        return findAll().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByCity(String city) {
        return findAll().stream()
                .filter(person -> person.getCity().equals(city))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(personMap.values());
    }

    @Override
    public void save(Person person) {
        personMap.put(Pair.of(person.getFirstName(), person.getLastName()), person);
        attributeMedicalRecord(person);
        calculateAgeAndAssignToPerson(person);
    }

    @Override
    public void saveAll(List<Person> list) {
        for (Person person : list) {
            save(person);
        }
    }

    @Override
    public void update(Person person) {
        Person personToUpdate = find(person.getFirstName(), person.getLastName());
        if (personToUpdate != null) {
            personToUpdate.setAddress(person.getAddress());
            personToUpdate.setCity(person.getCity());
            personToUpdate.setZip(person.getZip());
            personToUpdate.setPhone(person.getPhone());
            personToUpdate.setEmail(person.getEmail());
        } else {
            throw new PersonNotFoundException(person.getFirstName(), person.getLastName());
        }
    }

    @Override
    public void delete(Person person) {
        personMap.remove(Pair.of(person.getFirstName(), person.getLastName()));
    }

    public void attributeMedicalRecord(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        MedicalRecord matchingMedicalRecord = medicalRecordRepository.find(firstName, lastName);

        if (matchingMedicalRecord == null) {
            throw new MedicalRecordNotFoundException(firstName, lastName);
        }
        person.setMedicalRecord(matchingMedicalRecord);
    }

    public void calculateAgeAndAssignToPerson(Person person) {
        String birthdate = person.getMedicalRecord().getBirthdate();
        person.setAge(AgeCalculator.calculate(birthdate));
    }

}
