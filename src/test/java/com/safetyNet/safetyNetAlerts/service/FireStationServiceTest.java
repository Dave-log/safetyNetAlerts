package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import com.safetyNet.safetyNetAlerts.service.impl.FireStationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {
    @Mock
    FireStationRepository mockFireStationRepository;

    @InjectMocks
    FireStationServiceImpl fireStationService;

    @Test
    public void testFindByStationNumber() {
        Integer stationNumber = 0;
        FireStation expectedFireStation = FireStation.builder().stationNumber(stationNumber).build();

        when(mockFireStationRepository.find(stationNumber)).thenReturn(expectedFireStation);

        FireStation result = fireStationService.findByStationNumber(stationNumber);

        assertEquals(expectedFireStation, result);
    }

    @Test
    public void testFindByAddress_Found() {
        String address = "1 Main St";
        FireStation fireStation1 = FireStation.builder().stationNumber(1).addresses(Set.of("1 Main St", "2 Second St")).build();
        FireStation fireStation2 = FireStation.builder().stationNumber(2).addresses(Set.of("3 Third St")).build();

        when(mockFireStationRepository.findAll()).thenReturn(List.of(fireStation1, fireStation2));

        List<Integer> result = fireStationService.findByAddress(address);

        assertEquals(1, result.size());
        assertEquals(1, result.getFirst());
    }

    @Test
    public void testFindByAddress_NotFound() {
        String address = "4 Fourth St";
        FireStation fireStation1 = FireStation.builder().stationNumber(1).addresses(Set.of("1 Main St", "2 Second St")).build();
        FireStation fireStation2 = FireStation.builder().stationNumber(2).addresses(Set.of("3 Third St")).build();

        when(mockFireStationRepository.findAll()).thenReturn(List.of(fireStation1, fireStation2));

        List<Integer> result = fireStationService.findByAddress(address);

        assertEquals(0, result.size());
    }

    @Test
    public void testFindAll() {
        List<FireStation> expectedFireStations = List.of(
                FireStation.builder().stationNumber(5).build(),
                FireStation.builder().stationNumber(6).build()
        );
        when(mockFireStationRepository.findAll()).thenReturn(expectedFireStations);

        List<FireStation> result = fireStationService.findAll();

        assertEquals(expectedFireStations.size(), result.size());
        assertEquals(expectedFireStations, result);
    }

    @Test
    public void testCreate() {
        FireStation fireStation = FireStation.builder().stationNumber(0).build();

        fireStationService.create(fireStation);

        verify(mockFireStationRepository).save(fireStation);
    }

    @Test
    public void testCreates() {
        List<FireStation> fireStationList = List.of(
                FireStation.builder().stationNumber(5).build(),
                FireStation.builder().stationNumber(6).build()
        );

        fireStationService.create(fireStationList);

        verify(mockFireStationRepository).saveAll(fireStationList);
    }

    @Test
    public void testUpdate() {
        String address = "1 Main St";
        Integer newStationNumber = 0;

        fireStationService.update(address, newStationNumber);

        verify(mockFireStationRepository, times(1)).update(address, newStationNumber);
    }

    @Test
    public void testDeleteAddress() {
        String address = "1 Main St";
        Integer stationNumber = 0;

        fireStationService.deleteAddress(stationNumber, address);

        verify(mockFireStationRepository, times(1)).deleteAddress(stationNumber, address);
    }

    @Test
    public void testDeleteFireStation() {
        Integer stationNumber = 0;

        fireStationService.deleteFireStation(stationNumber);

        verify(mockFireStationRepository, times(1)).deleteFireStation(stationNumber);
    }

    @Test
    public void testFindPersonsCoveredByStation() {
        Integer stationNumber = 0;
        List<Person> expectedPersons = new ArrayList<>();

        when(mockFireStationRepository.findPersonsCoveredByStation(stationNumber)).thenReturn(expectedPersons);

        List<Person> result = fireStationService.findPersonsCoveredByStation(stationNumber);

        assertEquals(expectedPersons, result);
        verify(mockFireStationRepository, times(1)).findPersonsCoveredByStation(stationNumber);
    }

    @Test
    public void testGetPersonsCoveredByStationsSortedByAge() {
        Integer stationNumber = 0;
        List<Person> personsCoveredByStation = new ArrayList<>();

        when(mockFireStationRepository.findPersonsCoveredByStation(stationNumber)).thenReturn(personsCoveredByStation);

        FireStationDTO fireStationDTO = fireStationService.getPersonsCoveredByStationsSortedByAge(stationNumber);

        assertEquals(personsCoveredByStation, fireStationDTO.getPersonsCoveredByStation());
        verify(mockFireStationRepository, times(1)).findPersonsCoveredByStation(stationNumber);
    }
}
