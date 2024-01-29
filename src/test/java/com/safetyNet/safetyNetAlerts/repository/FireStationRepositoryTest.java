package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.exceptions.AddressNotFoundException;
import com.safetyNet.safetyNetAlerts.exceptions.StationNotFoundException;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.impl.FireStationRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationRepositoryTest {

    @InjectMocks
    FireStationRepositoryImpl fireStationRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    Map<Integer, FireStation> mockFireStationMap;

    @Test
    public void testFind_FireStationExists() {
        FireStation mockFireStation = FireStation.builder().stationNumber(0).addresses(Collections.emptySet()).build();

        when(mockFireStationMap.get(any())).thenReturn(mockFireStation);

        FireStation realFireStation = fireStationRepository.find(0);
        assertEquals(mockFireStation, realFireStation);
    }

    @Test
    public void testFind_FireStationNotExists() {
        when(mockFireStationMap.get(any())).thenReturn(null);

        FireStation fireStation = fireStationRepository.find(0);

        assertNull(fireStation);
    }

    @Test
    public void testFindAll_Success() {
        List<FireStation> mockFireStationList = new ArrayList<>();
        mockFireStationList.add(FireStation.builder().stationNumber(5).addresses(Collections.emptySet()).build());
        mockFireStationList.add(FireStation.builder().stationNumber(6).addresses(Collections.emptySet()).build());

        when(mockFireStationMap.values()).thenReturn(mockFireStationList);

        List<FireStation> fireStationList = fireStationRepository.findAll();

        assertEquals(mockFireStationList, fireStationList);
    }

    @Test
    public void testFindAll_EmptyList() {
        List<FireStation> emptyList = new ArrayList<>();

        when(mockFireStationMap.values()).thenReturn(emptyList);

        List<FireStation> result = fireStationRepository.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void testSave_Success() {
        Integer stationNumber = 0;

        FireStation fireStationToSave = FireStation.builder().stationNumber(stationNumber).addresses(Collections.emptySet()).build();

        when(mockFireStationMap.put(stationNumber, fireStationToSave)).thenReturn(null);

        fireStationRepository.save(fireStationToSave);

        verify(mockFireStationMap).put(stationNumber, fireStationToSave);
    }

    @Test
    public void testUpdate_Success() {
        Integer newStationNumber = 5;
        String address = "1 Main St";

        FireStation existingStation1 = FireStation.builder().stationNumber(1).addresses(new HashSet<>(Set.of(address))).build();
        FireStation existingStation2 = FireStation.builder().stationNumber(2).addresses(new HashSet<>(Set.of(address))).build();
        FireStation existingStation3 = FireStation.builder().stationNumber(newStationNumber).addresses(new HashSet<>(Set.of("2 Second St"))).build();

        mockFireStationMap.put(1, existingStation1);
        mockFireStationMap.put(2, existingStation2);
        mockFireStationMap.put(3, existingStation3);

        when(mockFireStationMap.values()).thenReturn(List.of(existingStation1, existingStation2, existingStation3));
        when(mockFireStationMap.get(newStationNumber)).thenReturn(existingStation3);
        when(mockFireStationMap.containsKey(newStationNumber)).thenReturn(true);

        fireStationRepository.update(address, newStationNumber);

        assertFalse(existingStation1.getAddresses().contains(address));
        assertFalse(existingStation2.getAddresses().contains(address));
        assertTrue(existingStation3.getAddresses().contains(address));
    }

    @Test
    public void testUpdate_StationNotFound() {
        Integer newStationNumber = 0;
        String address = "1 Main St";

        when(mockFireStationMap.containsKey(newStationNumber)).thenReturn(false);

        assertThrows(StationNotFoundException.class, () -> fireStationRepository.update(address, newStationNumber));
    }

    @Test
    public void testDeleteAddress_Success() {
        Integer stationNumber = 0;
        String addressToRemove = "1 Main St";

        FireStation fireStation = FireStation.builder().stationNumber(stationNumber).addresses(new HashSet<>(Set.of(addressToRemove))).build();

        when(mockFireStationMap.containsKey(stationNumber)).thenReturn(true);
        when(mockFireStationMap.get(stationNumber)).thenReturn(fireStation);

        fireStationRepository.deleteAddress(stationNumber, addressToRemove);

        assertFalse(fireStation.getAddresses().contains(addressToRemove));
    }

    @Test
    public void testDeleteAddress_StationNotFound() {
        Integer stationNumber = 0;
        String addressToRemove = "1 Main St";

        when(mockFireStationMap.containsKey(stationNumber)).thenReturn(false);

        assertThrows(StationNotFoundException.class, () -> fireStationRepository.deleteAddress(stationNumber, addressToRemove));
    }

    @Test
    public void testDeleteAddress_AddressNotFound() {
        Integer stationNumber = 0;
        String addressToRemove = "1 Main St";

        FireStation fireStation = FireStation.builder().stationNumber(stationNumber).addresses(new HashSet<>(Set.of("2 Second St"))).build();

        when(mockFireStationMap.containsKey(stationNumber)).thenReturn(true);
        when(mockFireStationMap.get(stationNumber)).thenReturn(fireStation);

        assertThrows(AddressNotFoundException.class, () -> fireStationRepository.deleteAddress(stationNumber, addressToRemove));

        assertTrue(fireStation.getAddresses().contains("2 Second St"));
        assertFalse(fireStation.getAddresses().contains(addressToRemove));
    }

    @Test
    public void testDeleteFireStation_Success() {
        Integer stationNumber = 0;

        when(mockFireStationMap.containsKey(stationNumber)).thenReturn(true);

        fireStationRepository.deleteFireStation(stationNumber);

        verify(mockFireStationMap, times(1)).remove(stationNumber);
    }

    @Test
    public void testDeleteFireStation_StationNotFound() {
        Integer stationNumber = 0;

        when(mockFireStationMap.containsKey(stationNumber)).thenReturn(false);

        assertThrows(StationNotFoundException.class, () -> fireStationRepository.deleteFireStation(stationNumber));
    }

    @Test
    public void testFindPersonsCoveredByStation() {
        Integer stationNumber = 0;

        List<Person> allPersons = new ArrayList<>();
        allPersons.add(Person.builder().firstName("John").lastName("Doe").address("1 Main St").city("Culver").build());
        allPersons.add(Person.builder().firstName("Jane").lastName("Doe").address("2 Second St").city("Culver").build());

        Set<String> addressesCoveredByStation = new HashSet<>(Set.of("1 Main St"));
        FireStation mockFireStation = FireStation.builder().stationNumber(stationNumber).addresses(addressesCoveredByStation).build();

        when(fireStationRepository.find(stationNumber)).thenReturn(mockFireStation);
        when(personRepository.findAll()).thenReturn(allPersons);

        List<Person> result = fireStationRepository.findPersonsCoveredByStation(stationNumber);

        verify(personRepository, times(1)).findAll();

        assertEquals(1, result.size());
        assertEquals("John", result.getFirst().getFirstName());
    }
}
