package com.safetyNet.safetyNetAlerts.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import lombok.Getter;

import java.util.List;

@Getter
public class FireDTO {
    @JsonView(Views.ResidentBase.class)
    private final List<Integer> servingFireStations;

    @JsonView(Views.ResidentBase.class)
    private final List<ResidentDTO> residents;

    public FireDTO(List<Integer> fireStationNumberList, List<ResidentDTO> fireResidents) {
        this.servingFireStations = fireStationNumberList;
        this.residents = fireResidents;
    }
}
