package com.safetyNet.safetyNetAlerts.exceptions;

public class MedicalRecordNotFoundException extends RuntimeException {
    public MedicalRecordNotFoundException(String firstName, String lastName) {
        super("Medical record not found for person: " + firstName + " " + lastName);
    }

    public MedicalRecordNotFoundException(String message) {
        super(message);
    }
}
