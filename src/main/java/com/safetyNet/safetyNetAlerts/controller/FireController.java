package com.safetyNet.safetyNetAlerts.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import com.safetyNet.safetyNetAlerts.dto.FireDTO;
import com.safetyNet.safetyNetAlerts.service.FireService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fire")
public class FireController {

    private static final Logger logger = LogManager.getLogger(FireController.class);

    @Autowired
    private FireService fireService;

    @GetMapping
    @JsonView(Views.ResidentWithPhone.class)
    public FireDTO getFireInfo(@RequestParam String address) {
        return fireService.getFireInfo(address);
    }
}
