package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoService {

    private static final Logger logger = LogManager.getLogger(PersonInfoService.class);

    @Autowired
    private PersonService personService;

    public ResidentDTO getPersonInfo(String firstName, String lastName) {
        Person matchingPerson = personService.findByFullName(firstName, lastName);
        ResidentDTO residentDTO;

        if (matchingPerson != null) {
            residentDTO =  new ResidentDTO(matchingPerson);
        } else {
            logger.error("The person " + firstName + " " + lastName + " does not exist.");
            residentDTO = new ResidentDTO();
        }

        return residentDTO;
    }
}
