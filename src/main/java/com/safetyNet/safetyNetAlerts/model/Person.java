package com.safetyNet.safetyNetAlerts.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String zip;
    private String city;
    private String phone;
    private String email;

    @JsonCreator
    public Person(@JsonProperty("firstName") String firstName,
                  @JsonProperty("lastName") String lastName,
                  @JsonProperty("address") String address,
                  @JsonProperty("zip") String zip,
                  @JsonProperty("city") String city,
                  @JsonProperty("phone") String phone,
                  @JsonProperty("email") String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() { return this.city; }

    public String getZip() { return this.zip; }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) { this.address = address; }

    public void setZip(String zip) { this.zip = zip; }

    public void setCity(String city) { this.city = city; }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "\n" + firstName + " " + lastName + "\n\taddress : " + address + ", " + zip + ", " + city +
                "\n\tphone : " + phone + "\n\temail : " + email + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
               Objects.equals(lastName, person.lastName) &&
               Objects.equals(address, person.address) &&
               Objects.equals(city, person.city) &&
               Objects.equals(zip, person.zip) &&
               Objects.equals(phone, person.phone) &&
               Objects.equals(email, person.email);
    }
}
