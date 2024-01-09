package com.safetyNet.safetyNetAlerts.model;

import com.fasterxml.jackson.annotation.*;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Person {

    @JsonProperty("firstName")
    @JsonView(Views.Base.class)
    private String firstName;

    @JsonProperty("lastName")
    @JsonView(Views.Base.class)
    private String lastName;

    @JsonProperty("address")
    @JsonView(Views.Base.class)
    private String address;

    @JsonProperty("city")
    @JsonView(Views.Base.class)
    private String city;

    @JsonProperty("zip")
    @JsonView(Views.Base.class)
    private String zip;

    @JsonProperty("phone")
    @JsonView(Views.Base.class)
    private String phone;

    @JsonProperty("email")
    @JsonView(Views.WithEmail.class)
    private String email;

    @JsonProperty("age")
    @JsonView(Views.WithAge.class)
    private int age;
}
