package com.safetyNet.safetyNetAlerts.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@Builder
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

    @JsonProperty("personsCovered")
    private List<Person> personsCovered;
}
