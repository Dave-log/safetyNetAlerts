package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Collection<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping
    public void create(@RequestBody Person person) {
        personService.create(person);
    }

    @PostMapping("/list")
    public void creates(@RequestBody List<Person> listPersons) {
        personService.creates(listPersons);
    }

    @PutMapping
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping
    public Person delete(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.delete(firstName, lastName);
    }
}
