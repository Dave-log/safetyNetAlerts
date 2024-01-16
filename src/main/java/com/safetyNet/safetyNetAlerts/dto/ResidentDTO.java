package com.safetyNet.safetyNetAlerts.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import lombok.Getter;

import java.util.List;

@Getter
public class ResidentDTO {

    @JsonView(Views.ResidentBase.class)
    private final String name;

    @JsonView(Views.ResidentWithAddressAndEmail.class)
    private final String address;

    @JsonView(Views.ResidentWithPhone.class)
    private final String phone;

    @JsonView(Views.ResidentBase.class)
    private final int age;

    @JsonView(Views.ResidentWithEmail.class)
    private final String email;

    @JsonView(Views.ResidentBase.class)
    private final List<String> medications;

    @JsonView(Views.ResidentBase.class)
    private final List<String> allergies;

    public ResidentDTO(Person person, MedicalRecord medicalRecord) {
        this.name = person.getFirstName() + " " + person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
        this.age = person.getAge();
        this.email = person.getEmail();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}
