package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FloodDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FloodServiceTest {
    @Mock
    private FireStationService mockFireStationService;

    @InjectMocks
    private FloodService floodService;

    @Test
    public void testGetFloodInfoByStations() {
        List<Integer> stationNumberList = List.of(1, 2);
        Map<String, List<ResidentDTO>> residentsByAddressMap = new HashMap<>();
        residentsByAddressMap.put("1 Main St", new ArrayList<>());
        residentsByAddressMap.put("2 Second St", new ArrayList<>());
        List<Person> personList = Arrays.asList(
                Person.builder().firstName("John").lastName("Doe").address("1 Main St").build(),
                Person.builder().firstName("Jane").lastName("Doe").address("2 Second St").build()
        );

        when(mockFireStationService.findPersonsCoveredByStation(anyInt())).thenReturn(personList);

        FloodDTO floodDTO = floodService.getFloodInfoByStations(stationNumberList);

        assertEquals(residentsByAddressMap.size(), floodDTO.getResidentsByAddress().size());
        assertEquals(residentsByAddressMap.keySet(), floodDTO.getResidentsByAddress().keySet());

        verify(mockFireStationService, times(2)).findPersonsCoveredByStation(anyInt());
    }
}
