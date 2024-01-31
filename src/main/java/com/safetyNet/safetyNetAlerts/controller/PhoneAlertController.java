package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.service.PhoneAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    @Autowired
    private PhoneAlertService phoneAlertService;

    @GetMapping
    public List<String> getPhoneAlert(@RequestParam Integer stationNumber) {
        return phoneAlertService.getPhoneAlert(stationNumber);
    }
}
