package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private static final Logger logger = LogManager.getLogger(FireStationController.class);

    @Autowired
    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    public Collection<FireStation> findAll() {
        return fireStationService.findAll();
    }

    @PostMapping
    public void create(@RequestBody FireStation fireStation) {
        fireStationService.create(fireStation);
    }

    @PostMapping("/list")
    public void creates(@RequestBody List<FireStation> listFireStations) {
        fireStationService.creates(listFireStations);
    }

    @PutMapping
    public FireStation update(@RequestBody FireStation fireStation) {
        return fireStationService.update(fireStation);
    }

    @DeleteMapping("/deleteByAddress")
    public void deleteFireStationByAddress(@RequestParam String station, @RequestParam String address) { fireStationService.deleteByAddress(station, address); }

    @DeleteMapping("/deleteByStation")
    public void deleteFireStationByStation(@RequestParam String station) { fireStationService.deleteByStation(station); }

}
