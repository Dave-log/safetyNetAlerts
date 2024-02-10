package com.safetyNet.safetyNetAlerts.dto;

import com.safetyNet.safetyNetAlerts.model.Person;
import lombok.Getter;

import java.util.List;

@Getter
public class FireStationDTO {

    private final List<Person> personsCoveredByStation;

    private final int numberOfAdults;
    private final int numberOfChildren;

    public FireStationDTO(List<Person> personsCoveredByStation, int numberOfAdults, int numberOfChildren){
        this.personsCoveredByStation = personsCoveredByStation;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }
}
