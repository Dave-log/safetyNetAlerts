package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FireDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireServiceTest {
    @Mock
    private PersonService mockPersonService;

    @Mock
    private FireStationService mockFireStationService;

    @InjectMocks
    private FireService fireService;

    @Test
    public void testGetFireInfo() {
        String address = "1 Main St";
        List<Integer> fireStationNumbers = List.of(1, 2);
        List<Person> residentList = new ArrayList<>();
        residentList.add(Person.builder().firstName("John").lastName("Doe").address(address).build());
        residentList.add(Person.builder().firstName("Jane").lastName("Doe").address(address).build());

        when(mockPersonService.findByAddress(address)).thenReturn(residentList);
        when(mockFireStationService.findByAddress(address)).thenReturn(fireStationNumbers);

        FireDTO fireDTO = fireService.getFireInfo(address);

        List<ResidentDTO> residentDTOList = fireDTO.getResidents();
        assertEquals(residentList.size(), residentDTOList.size());
        assertEquals("John Doe", residentDTOList.getFirst().getName());
        assertEquals("Jane Doe", residentDTOList.getLast().getName());
    }
}
