package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FireDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireService {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    public FireDTO getFireInfo(String address) {
        List<Person> residentList = personService.findByAddress(address);
        List<Integer> fireStationNumbers = fireStationService.findByAddress(address);
        List<ResidentDTO> residentDTOList = new ArrayList<>();

        for (Person resident : residentList) {
            residentDTOList.add(new ResidentDTO(resident));
        }

        return new FireDTO(fireStationNumbers, residentDTOList);
    }
}
