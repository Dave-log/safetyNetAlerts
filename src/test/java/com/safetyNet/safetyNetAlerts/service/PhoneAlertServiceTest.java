package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertServiceTest {
    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @Test
    public void getPhoneAlert_Success() {
        Integer stationNumber = 0;
        List<Person> personsCoveredByStation = Arrays.asList(
                new Person("John",
                        "Doe",
                        "1 Main St",
                        "Culver",
                        "00000",
                        "000-000-0000",
                        "john.doe@email.com",
                        30,
                        new MedicalRecord("John", "Doe", "01/01/1900", Collections.emptyList(), Collections.emptyList())),
                new Person("Jane",
                        "Doe",
                        "1 Main St",
                        "Culver",
                        "00000",
                        "111-111-1111",
                        "jane.doe@email.com",
                        32,
                        new MedicalRecord("Jane", "Doe", "01/01/1900", Collections.emptyList(), Collections.emptyList()))
        );

        when(fireStationService.findPersonsCoveredByStation(stationNumber)).thenReturn(personsCoveredByStation);

        List<String> result = phoneAlertService.getPhoneAlert(stationNumber);

        assertEquals(2, result.size());
        assertTrue(result.contains("000-000-0000"));
        assertTrue(result.contains("111-111-1111"));

        verify(fireStationService).findPersonsCoveredByStation(stationNumber);
    }

}
