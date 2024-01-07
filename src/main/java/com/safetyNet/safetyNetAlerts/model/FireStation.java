package com.safetyNet.safetyNetAlerts.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FireStation {

    @JsonProperty("station")
    private String station;

    @JsonProperty("address")
    private String address;

    @JsonProperty("addresses")
    private Set<String> addresses;

    public FireStation(String station, Set<String> addresses) {
        this.station = station;
        this.addresses = addresses;
    }
}
