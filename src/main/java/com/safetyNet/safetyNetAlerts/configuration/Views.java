package com.safetyNet.safetyNetAlerts.configuration;

/**
 * Views allow to filter the exposure of fields in the JSON response.
 */
public interface Views {

    public class PersonBase {}
    public class PersonWithAge extends PersonBase {}
    public class PersonWithEmail extends PersonBase {}

    public class ResidentBase {}
    public class ResidentWithEmail extends  ResidentBase {}
    public class ResidentWithPhone extends  ResidentBase {}
    public class ResidentWithAddressAndEmail extends ResidentWithEmail {}
}
