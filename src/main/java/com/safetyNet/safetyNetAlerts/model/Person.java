package com.safetyNet.safetyNetAlerts.model;

import com.fasterxml.jackson.annotation.*;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Person {

    @JsonProperty("firstName")
    @JsonView(Views.PersonBase.class)
    private String firstName;

    @JsonProperty("lastName")
    @JsonView(Views.PersonBase.class)
    private String lastName;

    @JsonProperty("address")
    @JsonView(Views.PersonBase.class)
    private String address;

    @JsonProperty("city")
    @JsonView(Views.PersonBase.class)
    private String city;

    @JsonProperty("zip")
    @JsonView(Views.PersonBase.class)
    private String zip;

    @JsonProperty("phone")
    @JsonView(Views.PersonBase.class)
    private String phone;

    @JsonProperty("email")
    @JsonView(Views.PersonWithEmail.class)
    private String email;

    @JsonProperty("age")
    @JsonView(Views.PersonWithAge.class)
    private int age;

    @JsonProperty("medicalRecord")
    private MedicalRecord medicalRecord;
}
