package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> createPersons(List<Person> listPersons) {
        return personRepository.saveAll(listPersons);
    }

    public Optional<Person> getPerson(String firstName, String lastName) {
        return personRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Person updatePerson(Person person) {

        Person existingPerson = null;
        Optional<Person> optionalPerson = getPerson(person.getFirstName(), person.getLastName());
        if (optionalPerson.isPresent()) {
            existingPerson = optionalPerson.get();
            existingPerson.setAddress(person.getAddress());
            existingPerson.setZip(person.getZip());
            existingPerson.setCity(person.getCity());
            existingPerson.setPhone(person.getPhone());
            existingPerson.setEmail(person.getEmail());
            personRepository.save(existingPerson);
            logger.info("The person " + person.getFirstName() + " " + person.getLastName() + " has been updated.");
        } else {
            createPerson(person);
            logger.warn("The person " + person.getFirstName() + " " + person.getLastName() + " does not exist and has been added to the database.");
        }
        return existingPerson;
    }

    public Person deletePerson(String firstName, String lastName) {

        Person existingPerson = null;
        Optional<Person> optionalPerson = getPerson(firstName, lastName);
        if (optionalPerson.isPresent()) {
            existingPerson = optionalPerson.get();
            personRepository.delete(existingPerson);
            logger.info("The person " + firstName + " " + lastName + " has been deleted from database.");
        } else {
            logger.warn("The person " + firstName + " " + lastName + " does not exist.");
        }
        return existingPerson;
    }
}
