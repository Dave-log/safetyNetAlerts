package com.safetyNet.safetyNetAlerts.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.service.EmergencyService;
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
    @Autowired
    private final EmergencyService emergencyService;


    public FireStationController(FireStationService fireStationService, EmergencyService emergencyService) {
        this.fireStationService = fireStationService;
        this.emergencyService = emergencyService;
    }

    @GetMapping
    @ResponseBody
    @JsonView(Views.PersonWithAge.class)
    public FireStationDTO getPersonsCoveredByStation(@RequestParam Integer stationNumber) {
        return emergencyService.getPersonsCoveredByStationsSortedByAge(stationNumber);
    }

    @GetMapping("/all")
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
    public void update(@RequestParam String address, @RequestParam Integer newStationNumber) {
        fireStationService.update(address, newStationNumber);
    }

    @DeleteMapping("/deleteByAddress")
    public void deleteFireStationByAddress(@RequestParam Integer stationNumber, @RequestParam String address) { fireStationService.deleteAddress(stationNumber, address); }

    @DeleteMapping("/deleteByStation")
    public void deleteFireStationByStation(@RequestParam Integer stationNumber) { fireStationService.deleteFireStation(stationNumber); }

}
