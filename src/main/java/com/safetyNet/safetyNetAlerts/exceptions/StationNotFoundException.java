package com.safetyNet.safetyNetAlerts.exceptions;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(Integer stationNumber) {
        super("Firestation n°" + stationNumber + " does not exist.");
    }

    public StationNotFoundException(String message) {
        super(message);
    }
}
