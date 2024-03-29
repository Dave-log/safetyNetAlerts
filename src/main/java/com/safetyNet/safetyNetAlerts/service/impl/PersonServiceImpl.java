package com.safetyNet.safetyNetAlerts.service.impl;

import com.safetyNet.safetyNetAlerts.dto.ChildAlertDTO;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import com.safetyNet.safetyNetAlerts.utils.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);

    @Autowired
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findByFullName(String firstName, String lastName) {
        return personRepository.find(firstName, lastName);
    }

    @Override
    public List<Person> findByAddress(String address) {
        return personRepository.findByAddress(address);
    }

    @Override
    public List<Person> findByCity(String city) {
        return personRepository.findByCity(city);
    }

    @Override
    public List<ChildAlertDTO> findChildrenByAddress(String address) {
        List<ChildAlertDTO> listChildren = new ArrayList<>();
        List<Person> listPersonsAtAddress = findByAddress(address);

        for (Person person : listPersonsAtAddress) {
            if (AgeCalculator.isChild(person.getAge())) {
                List<Person> householdMembers = listPersonsAtAddress.stream()
                        .filter(p -> !AgeCalculator.isChild(p.getAge()))
                        .toList();

                ChildAlertDTO childAlertDTO = new ChildAlertDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAge(),
                        householdMembers);
                listChildren.add(childAlertDTO);
            }
        }
        return listChildren;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public void create(Person person) {
        personRepository.save(person);
    }

    @Override
    public void create(List<Person> personList) {
        personRepository.saveAll(personList);
    }

    @Override
    public void update(Person person) {
        Person optionalPerson = findByFullName(person.getFirstName(), person.getLastName());
        if (optionalPerson != null) {
            personRepository.update(person);
            logger.info("The person " + person.getFirstName() + " " + person.getLastName() + " has been updated.");
        } else {
            logger.error("The person " + person.getFirstName() + " " + person.getLastName() + " does not exist.");
        }
    }

    @Override
    public void delete(String firstName, String lastName) {
        Person optionalPerson = findByFullName(firstName, lastName);
        if (optionalPerson != null) {
            personRepository.delete(optionalPerson);
            logger.info("The person " + firstName + " " + lastName + " has been deleted from data.");
        } else {
            logger.error("The person " + firstName + " " + lastName + " does not exist.");
        }
    }
}
