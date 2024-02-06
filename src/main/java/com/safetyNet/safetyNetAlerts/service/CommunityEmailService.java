package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommunityEmailService {

    @Autowired
    private PersonService personService;

    public Set<String> getCommunityEmails(String city) {
        Set<String> communityEmails = new HashSet<>();
        List<Person> personsByCity = personService.findByCity(city);

        for (Person person : personsByCity) {
            communityEmails.add(person.getEmail());
        }

        return communityEmails;
    }
}
