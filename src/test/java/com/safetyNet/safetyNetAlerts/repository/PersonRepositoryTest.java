package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.exceptions.MedicalRecordNotFoundException;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.impl.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonRepositoryTest {

    @InjectMocks
    private PersonRepositoryImpl personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private Map<Pair<String, String>, Person> mockPersonMap;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFind_PersonExists() {
        Person mockPerson = new Person(
                "John",
                "Boyd",
                "1509 Culver St",
                "Culver",
                "97451",
                "841-874-6512",
                "jaboyd@email.com",
                39,
                null);

        when(mockPersonMap.get(any())).thenReturn(mockPerson);

        Person realPerson = personRepository.find("John", "Boyd");

        assertEquals(mockPerson, realPerson);
    }

    @Test
    public void testFind_PersonNotExists() {
        when(mockPersonMap.get(any())).thenReturn(null);

        Person person = personRepository.find("John", "Doe");

        assertNull(person);
    }

    @Test
    public void testFindAll() {
        List<Person> mockPersonList = new ArrayList<>();
        mockPersonList.add(new Person("John", "Doe", 30));
        mockPersonList.add(new Person("Jane", "Doe", 32));

        when(mockPersonMap.values()).thenReturn(mockPersonList);

        List<Person> personList = personRepository.findAll();

        assertEquals(mockPersonList, personList);
    }

    @Test
    public void testFindAll_EmptyList() {
        List<Person> emptyList = new ArrayList<>();

        when(mockPersonMap.values()).thenReturn(emptyList);

        List<Person> result = personRepository.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void testFindByAddress_Success() {
        String targetAddress = "1 Main St";
        Person person1 = new Person("John", "Doe", targetAddress, "Culver");
        Person person2 = new Person("Jane", "Doe", targetAddress, "Culver");
        Person person3 = new Person("Bob", "Smith", "31 Oak St", "Culver");

        when(mockPersonMap.values()).thenReturn(List.of(person1, person2, person3));

        List<Person> result = personRepository.findByAddress(targetAddress);

        assertEquals(2, result.size());
        assertEquals(person1, result.getFirst());
        assertEquals(person2, result.getLast());

        verify(mockPersonMap, times(1)).values();
    }

    @Test
    public void testFindByCity_Success() {
        String targetCity = "Culver";
        Person person1 = new Person("John", "Doe", "1 Main St", "Culver");
        Person person2 = new Person("Jane", "Doe", "1 Main St", "Culver");
        Person person3 = new Person("Bob", "Smith", "1 Main St", "Boston");

        when(mockPersonMap.values()).thenReturn(List.of(person1, person2, person3));

        List<Person> result = personRepository.findByCity(targetCity);

        assertEquals(2, result.size());
        assertEquals(person1, result.getFirst());
        assertEquals(person2, result.getLast());

        verify(mockPersonMap, times(1)).values();
    }

    @Test
    public void testSave_Success() {
        MedicalRecord medicalRecord = new MedicalRecord(
                "John",
                "Doe",
                "10/27/1988",
                Collections.emptyList(),
                Collections.emptyList()
        );

        Person personToSave = new Person(
                "John",
                "Doe",
                "1 Main St",
                "Culver",
                "77777",
                "777-777-7777",
                "johndoe@email.com",
                0,
                null);

        when(medicalRecordRepository.find(personToSave.getFirstName(), personToSave.getLastName())).thenReturn(medicalRecord);
        when(mockPersonMap.put(Pair.of(personToSave.getFirstName(), personToSave.getLastName()), personToSave))
                .thenReturn(null);

        personRepository.save(personToSave);

        verify(mockPersonMap).put(Pair.of(personToSave.getFirstName(), personToSave.getLastName()), personToSave);
    }

    @Test
    public void testUpdate_Success() {
        Person existingPerson = new Person("John", "Doe", 30);
        existingPerson.setAddress("old address");
        existingPerson.setCity("old city");
        existingPerson.setZip("old zip");
        existingPerson.setPhone("old phone");
        existingPerson.setEmail("old email");

        Person updatedPerson = new Person("John", "Doe", 30);
        updatedPerson.setAddress("new address");
        updatedPerson.setCity("new city");
        updatedPerson.setZip("new zip");
        updatedPerson.setPhone("new phone");
        updatedPerson.setEmail("new email");

        when(mockPersonMap.get(Pair.of(updatedPerson.getFirstName(), updatedPerson.getLastName())))
                .thenReturn(existingPerson);

        personRepository.update(updatedPerson);

        verify(mockPersonMap).get(Pair.of(updatedPerson.getFirstName(), updatedPerson.getLastName()));

        assertEquals("new address", existingPerson.getAddress());
        assertEquals("new city", existingPerson.getCity());
        assertEquals("new zip", existingPerson.getZip());
        assertEquals("new phone", existingPerson.getPhone());
        assertEquals("new email", existingPerson.getEmail());
    }

    @Test
    public void testDelete_Success() {
        Person personToDelete = new Person("John", "Doe", 30);

        when(mockPersonMap.remove(Pair.of(personToDelete.getFirstName(), personToDelete.getLastName())))
                .thenReturn(personToDelete);

        personRepository.delete(personToDelete);

        verify(mockPersonMap).remove(Pair.of(personToDelete.getFirstName(), personToDelete.getLastName()));
    }

    @Test
    public void testAttributeMedicalRecord_MedicalRecordNotFound() {
        Person person = new Person("John", "Doe", 30);

        when(medicalRecordRepository.find(person.getFirstName(), person.getLastName())).thenReturn(null);

        assertThrows(MedicalRecordNotFoundException.class, () -> personRepository.attributeMedicalRecord(person));
    }
}
