package com.safetyNet.safetyNetAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService mockPersonService;

    @InjectMocks
    private PersonController personController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFind() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        Person mockPerson = new Person(firstName, lastName, 30);

        when(mockPersonService.findByFullName(anyString(), anyString())).thenReturn(mockPerson);

        mockMvc.perform(MockMvcRequestBuilders.get("/person")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(firstName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(lastName));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Person> mockPersonList = Arrays.asList(
                new Person("John", "Doe", 30),
                new Person("Jane", "Doe", 32)
        );

        when(mockPersonService.findAll()).thenReturn(mockPersonList);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/all")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockPersonList.size()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Jane"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Doe"));
    }

    @Test
    public void testCreate() throws Exception {
        Person newMockPerson = new Person("John", "Doe", 30);
        String newMockPersonToJson = objectMapper.writeValueAsString(newMockPerson);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newMockPersonToJson))
                        .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockPersonService).create(newMockPerson);
    }

    @Test
    public void testUpdate() throws Exception {
        Person updatedMockPerson = new Person("John", "Doe", 30);
        String updatedMockPersonToJson = objectMapper.writeValueAsString(updatedMockPerson);

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedMockPersonToJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockPersonService).update(updatedMockPerson);
    }

    @Test
    public void testDelete() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockPersonService).delete(firstName, lastName);
    }

}
