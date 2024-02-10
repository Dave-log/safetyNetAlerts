package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.dto.ChildAlertDTO;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ChildAlertController.class)
public class ChildAlertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService mockPersonService;

    @InjectMocks
    private ChildAlertController childAlertController;

    @Test
    public void testGetChildAlert_Success() throws Exception {
        String address = "1 Main St";

        when(mockPersonService.findChildrenByAddress(address)).thenReturn(createChildAlertDTOList());

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .param("address", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Jack"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].householdMembers.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("June"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].householdMembers.length()").value(1));

        verify(mockPersonService).findChildrenByAddress(address);
    }

    @Test
    public void testGetChildAlert_NotFound() throws Exception {
        String address = "1 Main St";

        when(mockPersonService.findChildrenByAddress(address)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .param("address", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());

        verify(mockPersonService).findChildrenByAddress(address);
    }

    private List<ChildAlertDTO> createChildAlertDTOList() {
        Person child1 = Person.builder().firstName("Jack").lastName("Doe").age(8).build();
        Person child2 = Person.builder().firstName("June").lastName("Doe").age(5).build();
        List<Person> householdMembers1 = Arrays.asList(
                Person.builder().firstName("John").lastName("Doe").age(30).build(),
                Person.builder().firstName("Jane").lastName("Doe").age(32).build());
        List<Person> householdMembers2 = List.of(Person.builder().firstName("Joy").lastName("Doe").age(42).build());

        ChildAlertDTO childAlertDTO1 = new ChildAlertDTO(
                child1.getFirstName(),
                child1.getLastName(),
                child1.getAge(),
                householdMembers1
        );
        ChildAlertDTO childAlertDTO2 = new ChildAlertDTO(
                child2.getFirstName(),
                child2.getLastName(),
                child2.getAge(),
                householdMembers2
        );

        return Arrays.asList(childAlertDTO1, childAlertDTO2);
    }
}
