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
        FireStation mockFireStation = new FireStation(0, Collections.emptySet());

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
        mockFireStationList.add(new FireStation(5, Collections.emptySet()));
        mockFireStationList.add(new FireStation(6, Collections.emptySet()));

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

        FireStation fireStationToSave = new FireStation(stationNumber, Collections.emptySet());

        when(mockFireStationMap.put(stationNumber, fireStationToSave)).thenReturn(null);

        fireStationRepository.save(fireStationToSave);

        verify(mockFireStationMap).put(stationNumber, fireStationToSave);
    }

    @Test
    public void testUpdate_Success() {
        Integer newStationNumber = 5;
        String address = "1 Main St";

        FireStation existingStation = new FireStation(1, new HashSet<>(Set.of(address)));

        when(mockFireStationMap.containsKey(newStationNumber)).thenReturn(true);
        when(mockFireStationMap.get(newStationNumber)).thenReturn(existingStation);

        fireStationRepository.update(address, newStationNumber);

        assertTrue(mockFireStationMap.get(newStationNumber).getAddresses().contains(address));
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

        FireStation fireStation = new FireStation(stationNumber, new HashSet<>(Set.of(addressToRemove)));

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

        FireStation fireStation = new FireStation(stationNumber, new HashSet<>(Set.of("2 Second St")));

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
        allPersons.add(new Person("John", "Doe", "1 Main St", "Culver"));
        allPersons.add(new Person("Jane", "Doe", "2 Second St", "Culver"));

        Set<String> addressesCoveredByStation = new HashSet<>(Set.of("1 Main St"));
        FireStation mockFireStation = new FireStation(stationNumber, addressesCoveredByStation);

        when(fireStationRepository.find(stationNumber)).thenReturn(mockFireStation);
        when(personRepository.findAll()).thenReturn(allPersons);

        List<Person> result = fireStationRepository.findPersonsCoveredByStation(stationNumber);

        verify(personRepository, times(1)).findAll();

        assertEquals(1, result.size());
        assertEquals("John", result.getFirst().getFirstName());
    }
}
