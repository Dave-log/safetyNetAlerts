package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/list")
    public List<Person> list() {
        return personService.getPersons();
    }

    @PostMapping("/add")
    public Person addPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PostMapping("/addlist")
    public List<Person> addPersons(@RequestBody List<Person> listPersons) {
        return personService.createPersons(listPersons);
    }

    @PutMapping("/update")
    public Person updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("/delete")
    public Person deletePerson(@RequestParam String firstName, @RequestParam String lastName) { return personService.deletePerson(firstName, lastName); }

    //@DeleteMapping("/delete/{firstName}/{lastName}")
    //public Person deletePerson(@PathVariable String firstName, @PathVariable String lastName) { return personService.deletePerson(firstName, lastName); }
}
