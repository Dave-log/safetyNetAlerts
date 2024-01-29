package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.exceptions.MedicalRecordNotFoundException;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.impl.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

    @InjectMocks
    private PersonRepositoryImpl personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private Map<Pair<String, String>, Person> mockPersonMap;

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
    public void testFindAll_Success() {
        List<Person> mockPersonList = new ArrayList<>();
        mockPersonList.add(Person.builder().firstName("John").lastName("Doe").age(30).build());
        mockPersonList.add(Person.builder().firstName("Jane").lastName("DOe").age(32).build());

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
        Person person1 = Person.builder().firstName("John").lastName("DOe").address(targetAddress).build();
        Person person2 = Person.builder().firstName("Jane").lastName("DOe").address(targetAddress).build();
        Person person3 = Person.builder().firstName("Bob").lastName("Smith").address("31 Oak St").build();

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
        Person person1 = Person.builder().lastName("John").lastName("Doe").city(targetCity).build();
        Person person2 = Person.builder().lastName("Jane").lastName("Doe").city(targetCity).build();
        Person person3 = Person.builder().lastName("Bob").lastName("Smith").city("Boston").build();

        when(mockPersonMap.values()).thenReturn(List.of(person1, person2, person3));

        List<Person> result = personRepository.findByCity(targetCity);

        assertEquals(2, result.size());
        assertEquals(person1, result.getFirst());
        assertEquals(person2, result.getLast());

        verify(mockPersonMap, times(1)).values();
    }

    @Test
    public void testSave_Success() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecord medicalRecord = new MedicalRecord(
                firstName,
                lastName,
                "10/27/1988",
                Collections.emptyList(),
                Collections.emptyList()
        );

        Person personToSave = new Person(
                firstName,
                lastName,
                "1 Main St",
                "Culver",
                "77777",
                "777-777-7777",
                "johndoe@email.com",
                0,
                null);

        when(medicalRecordRepository.find(firstName, lastName)).thenReturn(medicalRecord);
        when(mockPersonMap.put(Pair.of(firstName, lastName), personToSave)).thenReturn(null);

        personRepository.save(personToSave);

        verify(mockPersonMap).put(Pair.of(firstName, lastName), personToSave);
    }

    @Test
    public void testUpdate_Success() {
        String firstName = "John";
        String lastName = "Doe";

        Person existingPerson = Person.builder().firstName(firstName).lastName(lastName).age(30).build();
        existingPerson.setAddress("old address");
        existingPerson.setCity("old city");
        existingPerson.setZip("old zip");
        existingPerson.setPhone("old phone");
        existingPerson.setEmail("old email");

        Person updatedPerson = Person.builder().firstName(firstName).lastName(lastName).age(30).build();
        updatedPerson.setAddress("new address");
        updatedPerson.setCity("new city");
        updatedPerson.setZip("new zip");
        updatedPerson.setPhone("new phone");
        updatedPerson.setEmail("new email");

        when(mockPersonMap.get(Pair.of(firstName, lastName))).thenReturn(existingPerson);

        personRepository.update(updatedPerson);

        verify(mockPersonMap).get(Pair.of(firstName, lastName));

        assertEquals("new address", existingPerson.getAddress());
        assertEquals("new city", existingPerson.getCity());
        assertEquals("new zip", existingPerson.getZip());
        assertEquals("new phone", existingPerson.getPhone());
        assertEquals("new email", existingPerson.getEmail());
    }

    @Test
    public void testDelete_Success() {
        String firstName = "John";
        String lastName = "Doe";

        Person personToDelete = Person.builder().firstName(firstName).lastName(lastName).age(30).build();

        when(mockPersonMap.remove(Pair.of(firstName, lastName))).thenReturn(personToDelete);

        personRepository.delete(personToDelete);

        verify(mockPersonMap).remove(Pair.of(firstName, lastName));
    }

    @Test
    public void testAttributeMedicalRecord_MedicalRecordNotFound() {
        String firstName = "John";
        String lastName = "Doe";

        Person person = Person.builder().firstName(firstName).lastName(lastName).age(30).build();

        when(medicalRecordRepository.find(firstName, lastName)).thenReturn(null);

        assertThrows(MedicalRecordNotFoundException.class, () -> personRepository.attributeMedicalRecord(person));
    }
}
