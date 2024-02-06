package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {

    @Mock
    private PersonService mockPersonService;

    @InjectMocks
    private CommunityEmailService communityEmailService;

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
        when(mockPersonService.findByCity(city)).thenReturn(personsByCity);

        Set<String> result = communityEmailService.getCommunityEmails(city);

        assertEquals(expectedEmails.size(), result.size());
        assertEquals(expectedEmails, result);
    }

}
