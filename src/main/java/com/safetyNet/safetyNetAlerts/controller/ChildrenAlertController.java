package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.dto.ChildAlertDTO;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/childAlert")
public class ChildrenAlertController {

    private static final Logger logger = LogManager.getLogger(ChildrenAlertController.class);

    @Autowired
    private final PersonService personService;

    public ChildrenAlertController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<ChildAlertDTO>> getChildrenAlert(@RequestParam String address) {
        List<ChildAlertDTO> listChildrenAlert = personService.findChildrenByAddress(address);
        if (listChildrenAlert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listChildrenAlert);
    }
}
