package com.safetyNet.safetyNetAlerts.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class FloodDTO {

    @JsonView(Views.ResidentBase.class)
    private final Map<String, List<ResidentDTO>> residentsByAddress;

    public FloodDTO(Map<String, List<ResidentDTO>> residentsByAddressMap) {
        this.residentsByAddress = residentsByAddressMap;
    }
}
