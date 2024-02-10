package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    @Autowired
    private final FireStationService fireStationService;


    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    public FireStationDTO getPersonsCoveredByStation(@RequestParam Integer stationNumber) {
        return fireStationService.getPersonsCoveredByStationsSortedByAge(stationNumber);
    }

    @GetMapping("/all")
    public List<FireStation> findAll() {
        return fireStationService.findAll();
    }

    @PostMapping
    public void create(@RequestBody FireStation fireStation) {
        fireStationService.create(fireStation);
    }

    @PostMapping("/list")
    public void create(@RequestBody List<FireStation> listFireStations) {
        fireStationService.create(listFireStations);
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
