package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.dto.ChildAlertDTO;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {

    @Autowired
    private final PersonService personService;

    public ChildAlertController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<ChildAlertDTO> getChildAlert(@RequestParam String address) {
        return personService.findChildrenByAddress(address);
    }
}
