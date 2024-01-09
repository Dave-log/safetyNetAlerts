package com.safetyNet.safetyNetAlerts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.EmergencyService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
public class FireStationDTO {

    @Autowired
    @JsonIgnore
    private EmergencyService emergencyService;

    @JsonView(Views.WithAge.class)
    private final List<Person> personsCoveredByStation;

    private final int numberOfAdults;
    private final int numberOfChildren;

    public FireStationDTO(List<Person> personsCoveredByStation, int numberOfAdults, int numberOfChildren){
        this.personsCoveredByStation = personsCoveredByStation;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }
}
