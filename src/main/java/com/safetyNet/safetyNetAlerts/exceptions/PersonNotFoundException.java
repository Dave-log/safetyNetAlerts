package com.safetyNet.safetyNetAlerts.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String firstName, String lastName) {
        super("Person " + firstName + " " + lastName + " does not exist.");
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}
