package com.safetyNet.safetyNetAlerts.repository.impl;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private static final Logger logger = LogManager.getLogger(PersonRepositoryImpl.class);

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
        personToUpdate.setAddress(person.getAddress());
        personToUpdate.setCity(person.getCity());
        personToUpdate.setZip(person.getZip());
        personToUpdate.setPhone(person.getPhone());
        personToUpdate.setEmail(person.getEmail());
    }

    @Override
    public void delete(Person person) {
        personMap.remove(Pair.of(person.getFirstName(), person.getLastName()));
    }
}
