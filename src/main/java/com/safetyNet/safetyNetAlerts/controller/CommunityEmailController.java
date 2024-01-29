package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    private static final Logger logger = LogManager.getLogger(CommunityEmailController.class);

    @Autowired
    PersonService personService;

    @GetMapping
    public Set<String> getCommunityEmails(@RequestParam String city) {
        return personService.getCommunityEmails(city);
    }
}
