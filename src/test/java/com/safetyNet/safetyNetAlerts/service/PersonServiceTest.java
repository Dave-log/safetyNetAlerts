package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.dto.ChildAlertDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.PersonRepository;
import com.safetyNet.safetyNetAlerts.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository mockPersonRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void testFindByFullName() {
        String firstName = "John";
        String lastName = "Doe";
        Person expectedPerson = Person.builder().firstName(firstName).lastName(lastName).build();

        when(mockPersonRepository.find(firstName, lastName)).thenReturn(expectedPerson);

        Person result = personService.findByFullName(firstName, lastName);

        assertEquals(expectedPerson, result);
    }

    @Test
    public void testFindByAddress() {
        String address = "1 Main St";
        List<Person> expectedPersons = List.of(
                Person.builder().firstName("John").lastName("Doe").build(),
                Person.builder().firstName("Jane").lastName("Doe").build()
        );
        when(mockPersonRepository.findByAddress(address)).thenReturn(expectedPersons);

        List<Person> result = personService.findByAddress(address);

        assertEquals(expectedPersons, result);
    }

    @Test
    public void testFindByCity() {
        String city = "Paris";
        List<Person> expectedPersons = List.of(
                Person.builder().firstName("John").lastName("Doe").build(),
                Person.builder().firstName("Jane").lastName("Doe").build()
        );
        when(mockPersonRepository.findByCity(city)).thenReturn(expectedPersons);

        List<Person> result = personService.findByCity(city);

        assertEquals(expectedPersons, result);
    }

    @Test
    public void testFindChildrenByAddress() {
        String address = "1 Main St";
        List<Person> personsAtAddress = List.of(
                Person.builder().firstName("John").lastName("Doe").age(30).build(),
                Person.builder().firstName("Jane").lastName("Doe").age(32).build(),
                Person.builder().firstName("Jack").lastName("Doe").age(8).build(),
                Person.builder().firstName("Joy").lastName("Doe").age(5).build()
        );
        when(mockPersonRepository.findByAddress(address)).thenReturn(personsAtAddress);

        List<ChildAlertDTO> result = personService.findChildrenByAddress(address);

        assertEquals(2, result.size());
        assertEquals("Jack", result.getFirst().getFirstName());
        assertEquals("Joy", result.getLast().getFirstName());
        assertEquals(2, result.getFirst().getHouseholdMembers().size());
    }

    @Test
    public void testFindAll() {
        List<Person> expectedPersons = List.of(
                Person.builder().firstName("John").lastName("Doe").build(),
                Person.builder().firstName("Jane").lastName("Doe").build()
        );
        when(mockPersonRepository.findAll()).thenReturn(expectedPersons);

        List<Person> result = personService.findAll();

        assertEquals(expectedPersons.size(), result.size());
        assertEquals(expectedPersons, result);
    }

    @Test
    public void testGetCommunityEmails() {
        String city = "Paris";
        String email1 = "johndoe@email.com";
        String email2 = "janedoe@email.com";
        Set<String> expectedEmails = Set.of(email1, email2);
        List<Person> personsByCity = List.of(
                Person.builder().firstName("John").lastName("Doe").email(email1).build(),
                Person.builder().firstName("Jane").lastName("Doe").email(email2).build()
        );
        when(mockPersonRepository.findByCity(city)).thenReturn(personsByCity);

        Set<String> result = personService.getCommunityEmails(city);

        assertEquals(expectedEmails.size(), result.size());
        assertEquals(expectedEmails, result);
    }

    @Test
    public void testCreate() {
        Person person = Person.builder().firstName("John").lastName("Doe").build();

        personService.create(person);

        verify(mockPersonRepository).save(person);
    }

    @Test
    public void testCreates() {
        List<Person> personList = List.of(
                Person.builder().firstName("John").lastName("Doe").build(),
                Person.builder().firstName("Jane").lastName("Doe").build()
        );

        personService.creates(personList);

        verify(mockPersonRepository).saveAll(personList);
    }

    @Test
    public void testUpdate_PersonExists() {
        String firstName = "John";
        String lastName = "Doe";
        Person person = Person.builder().firstName(firstName).lastName(lastName).build();
        when(mockPersonRepository.find(firstName, lastName)).thenReturn(person);

        personService.update(person);

        verify(mockPersonRepository, times(1)).update(person);
    }

    @Test
    public void testUpdate_PersonNotExists() {
        String firstName = "John";
        String lastName = "Doe";
        Person person = Person.builder().firstName(firstName).lastName(lastName).build();
        when(mockPersonRepository.find(firstName, lastName)).thenReturn(null);

        personService.update(person);

        verify(mockPersonRepository, never()).update(person);
    }

    @Test
    public void testDelete_PersonExists() {
        String firstName = "John";
        String lastName = "Doe";
        Person person = Person.builder().firstName(firstName).lastName(lastName).build();
        when(mockPersonRepository.find(firstName, lastName)).thenReturn(person);

        personService.delete(firstName, lastName);

        verify(mockPersonRepository, times(1)).delete(person);
    }

    @Test
    public void testDelete_PersonNotExists() {
        String firstName = "John";
        String lastName = "Doe";
        when(mockPersonRepository.find(firstName, lastName)).thenReturn(null);

        personService.delete(firstName, lastName);

        verify(mockPersonRepository, never()).delete(any());
    }

    @Test
    public void testGetPersonInfo_PersonExists() {
        String firstName = "John";
        String lastName = "Doe";
        Person person = Person.builder().firstName(firstName).lastName(lastName).build();
        when(mockPersonRepository.find(firstName, lastName)).thenReturn(person);

        ResidentDTO residentDTO = personService.getPersonInfo(firstName, lastName);

        assertEquals("John Doe", residentDTO.getName());
    }

    @Test
    public void testGetPersonInfo_PersonNotExists() {
        String firstName = "John";
        String lastName = "Doe";
        when(mockPersonRepository.find(firstName, lastName)).thenReturn(null);

        ResidentDTO residentDTO = personService.getPersonInfo(firstName, lastName);

        assertNull(residentDTO.getName());
    }
}
