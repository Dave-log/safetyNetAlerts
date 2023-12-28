package com.safetyNet.safetyNetAlerts.model;

import org.springframework.stereotype.Component;

public class FireStation {
    private String address;
    private int stationNumber;

    public FireStation(String address, int stationNumber) {
        this.address = address;
        this.stationNumber = stationNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public int getStationNumber() {
        return this.stationNumber;
    }

    public void setAddress(String address) { this.address = address; }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }
}
