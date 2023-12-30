package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Iterable<Person> list() {
        return personRepository.findAll();
    }
    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Iterable<Person> saveAll(List<Person> listPerson) {
        return personRepository.saveAll(listPerson);
    }

}
