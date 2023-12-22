package com.safetyNet.safetyNetAlerts.model;

import org.springframework.stereotype.Component;

public class Person {

    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private String email;

    public Person(String firstName, String lastName, Address address, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
