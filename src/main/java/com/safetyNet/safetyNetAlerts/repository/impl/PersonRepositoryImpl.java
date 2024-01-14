package com.safetyNet.safetyNetAlerts.repository.impl;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final static Logger logger = LogManager.getLogger(PersonRepositoryImpl.class);

    @Override
    public Person find(String firstName, String lastName) {
        return personMap.get(Pair.of(firstName, lastName));
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
