package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.service.CommunityEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    @Autowired
    CommunityEmailService communityEmailService;

    @GetMapping
    public Set<String> getCommunityEmails(@RequestParam String city) {
        return communityEmailService.getCommunityEmails(city);
    }
}
