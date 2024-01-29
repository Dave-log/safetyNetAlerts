package com.safetyNet.safetyNetAlerts.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetyNet.safetyNetAlerts.configuration.Views;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResidentDTO {

    @JsonView(Views.ResidentBase.class)
    private String name;

    @JsonView(Views.ResidentWithAddressAndEmail.class)
    private String address;

    @JsonView(Views.ResidentWithPhone.class)
    private String phone;

    @JsonView(Views.ResidentBase.class)
    private int age;

    @JsonView(Views.ResidentWithEmail.class)
    private String email;

    @JsonView(Views.ResidentBase.class)
    private List<String> medications;

    @JsonView(Views.ResidentBase.class)
    private List<String> allergies;

    public ResidentDTO(Person person) {
        this.name = person.getFirstName() + " " + person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
        this.age = person.getAge();
        this.email = person.getEmail();
        this.medications = person.getMedicalRecord() != null ? person.getMedicalRecord().getMedications() : null;
        this.allergies = person.getMedicalRecord() != null ? person.getMedicalRecord().getAllergies() : null;
    }
}
