package com.safetyNet.safetyNetAlerts.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @JsonView(Views.PersonBase.class)
    public Person find(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.findByFullName(firstName, lastName);
    }

    @GetMapping("/all")
    @JsonView(Views.PersonBase.class)
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping
    public void create(@RequestBody Person person) {
        personService.create(person);
    }

    @PostMapping("/list")
    public void create(@RequestBody List<Person> personList) {
        personService.create(personList);
    }

    @PutMapping
    public void update(@RequestBody Person person) {
        personService.update(person);
    }

    @DeleteMapping
    public void delete(@RequestParam String firstName, @RequestParam String lastName) {
        personService.delete(firstName, lastName);
    }
}
