package com.safetyNet.safetyNetAlerts.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.View;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    private static final Logger logger = LogManager.getLogger(PersonInfoController.class);

    @Autowired
    PersonService personService;

    @GetMapping
    @JsonView(Views.ResidentWithAddressAndEmail.class)
    public ResidentDTO getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }
}
