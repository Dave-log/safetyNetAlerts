package com.safetyNet.safetyNetAlerts.model;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    public MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = new ArrayList<>(medications);
        this.allergies = new ArrayList<>(allergies);
    }

    public String getFirstName() { return this.firstName; }

    public String getLastName() { return this.lastName; }

    public String getBirthdate() {
        return this.birthdate;
    }

    public List<String> getMedications() {
        return this.medications;
    }

    public List<String> getAllergies() {
        return this.allergies;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setMedications(List<String> medications) {
        this.medications = new ArrayList<>(medications);
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = new ArrayList<>(allergies);
    }
}
