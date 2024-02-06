package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {

    @Mock
    private PersonService mockPersonService;

    @InjectMocks
    private PersonInfoService personInfoService;

    @Test
    public void testGetPersonInfo_PersonExists() {
        String firstName = "John";
        String lastName = "Doe";
        Person person = Person.builder().firstName(firstName).lastName(lastName).build();
        when(mockPersonService.findByFullName(firstName, lastName)).thenReturn(person);

        ResidentDTO residentDTO = personInfoService.getPersonInfo(firstName, lastName);

        assertEquals("John Doe", residentDTO.getName());
    }

    @Test
    public void testGetPersonInfo_PersonNotExists() {
        String firstName = "John";
        String lastName = "Doe";
        when(mockPersonService.findByFullName(firstName, lastName)).thenReturn(null);

        ResidentDTO residentDTO = personInfoService.getPersonInfo(firstName, lastName);

        assertNull(residentDTO.getName());
    }
}
