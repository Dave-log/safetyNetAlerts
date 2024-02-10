package com.safetyNet.safetyNetAlerts.dto;

import com.safetyNet.safetyNetAlerts.model.Person;
import lombok.Getter;

import java.util.List;

@Getter
public class ChildAlertDTO {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final List<Person> householdMembers;

    public ChildAlertDTO(String firstName, String lastName, int age, List<Person> householdMembers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.householdMembers = householdMembers;
    }
}
