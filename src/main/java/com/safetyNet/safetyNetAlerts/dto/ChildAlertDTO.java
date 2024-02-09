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

    public ChildAlertDTO(Person child, List<Person> householdMembers) {
        this.firstName = child.getFirstName();
        this.lastName = child.getLastName();
        this.age = child.getAge();
        this.householdMembers = householdMembers;
    }
}
