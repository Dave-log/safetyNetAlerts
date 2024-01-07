package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PersonService implements EntityService<Person> {

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    @Autowired
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Collection<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public void create(Person person) {
        personRepository.save(person);
    }

    @Override
    public void creates(List<Person> listPersons) {
        personRepository.saveAll(listPersons);
    }

    public Person findByFullName(String firstName, String lastName) {
        return personRepository.find(firstName, lastName);
    }

    @Override
    public Person update(Person person) {

        Person existingPerson = null;
        Person optionalPerson = findByFullName(person.getFirstName(), person.getLastName());
        if (optionalPerson != null) {
            existingPerson = optionalPerson;
            personRepository.save(existingPerson);
            logger.info("The person " + person.getFirstName() + " " + person.getLastName() + " has been updated.");
        } else {
            logger.error("The person " + person.getFirstName() + " " + person.getLastName() + " does not exist.");
        }
        return existingPerson;
    }

    public Person delete(String firstName, String lastName) {

        Person existingPerson = null;
        Person optionalPerson = findByFullName(firstName, lastName);
        if (optionalPerson != null) {
            existingPerson = optionalPerson;
            personRepository.delete(firstName, lastName);
            logger.info("The person " + firstName + " " + lastName + " has been deleted from database.");
        } else {
            logger.error("The person " + firstName + " " + lastName + " does not exist.");
        }
        return existingPerson;
    }

    @Override
    public Person delete(){ return null; }
}
