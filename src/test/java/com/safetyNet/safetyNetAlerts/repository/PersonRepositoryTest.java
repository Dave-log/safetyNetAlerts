package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.repository.impl.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonRepositoryTest {

    @InjectMocks
    private PersonRepositoryImpl personRepository;

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
                39);

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
}
