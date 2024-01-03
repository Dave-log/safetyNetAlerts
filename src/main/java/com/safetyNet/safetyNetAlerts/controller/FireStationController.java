package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private static final Logger logger = LogManager.getLogger(FireStationController.class);

    @Autowired
    private FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/list")
    public Iterable<FireStation> list() {
        return fireStationService.getFireStations();
    }

    @PostMapping("/add")
    public FireStation addFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.createFireStation(fireStation);
    }

    @PostMapping("/addlist")
    public List<FireStation> addPersons(@RequestBody List<FireStation> listFireStations) {
        return fireStationService.createFireStations(listFireStations);
    }

    @PutMapping("/update")
    public FireStation updatePerson(@RequestBody FireStation fireStation) {
        return fireStationService.updateFireStation(fireStation);
    }

    @DeleteMapping("/deleteByAddress")
    public FireStation deleteFireStationByAddress(@RequestParam String address) { return fireStationService.deleteFireStationByAddress(address); }

    @DeleteMapping("/deleteByStation")
    public List<FireStation> deleteFireStationByStation(@RequestParam String station) { return fireStationService.deleteFireStationByStation(station); }

}
