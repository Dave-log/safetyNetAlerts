package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersonRepository implements EntityRepository<Person> {

    private final static Logger logger = LogManager.getLogger(PersonRepository.class);

    private final Map<Pair<String, String>, Person> personMap = new HashMap<>();

    public Person find(String firstName, String lastName) {
        return personMap.get(Pair.of(firstName, lastName));
    }

    @Override
    public void save(Person person) {
        personMap.put(Pair.of(person.getFirstName(), person.getLastName()), person);
    }

    public void delete(String firstName, String lastName) {
        personMap.remove(Pair.of(firstName, lastName));
    }

    @Override
    public void saveAll(Collection<Person> list) {
        for (Person person : list) {
            Pair<String, String> key = Pair.of(person.getFirstName(), person.getLastName());
            personMap.put(key, person);
        }
    }

    @Override
    public Collection<Person> findAll() {
        return personMap.values();
    }

    @Override
    public Person find(){ return null; }

    @Override
    public void delete(){}
}
