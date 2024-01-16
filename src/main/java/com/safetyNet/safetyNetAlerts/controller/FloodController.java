package com.safetyNet.safetyNetAlerts.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import com.safetyNet.safetyNetAlerts.dto.FloodDTO;
import com.safetyNet.safetyNetAlerts.service.FloodService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flood")
public class FloodController {

    private static final Logger logger = LogManager.getLogger(FloodController.class);

    @Autowired
    private FloodService floodService;

    @GetMapping("/stations")
    @JsonView(Views.ResidentWithPhone.class)
    public ResponseEntity<FloodDTO> getFloodInfo(@RequestParam List<Integer> stations) {
        return ResponseEntity.ok(floodService.getFloodInfoByStations(stations));
    }

}
