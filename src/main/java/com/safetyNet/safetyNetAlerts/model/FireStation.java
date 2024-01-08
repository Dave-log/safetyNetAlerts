package com.safetyNet.safetyNetAlerts.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FireStation {

    @JsonProperty("station")
    private Integer stationNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("addresses")
    private Set<String> addresses;

    public FireStation(Integer stationNumber, Set<String> addresses) {
        this.stationNumber = stationNumber;
        this.addresses = addresses;
    }
}
