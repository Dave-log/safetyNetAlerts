package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
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

import java.util.Collections;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = PersonInfoController.class)
public class PersonInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService mockPersonService;

    @InjectMocks
    private PersonInfoController personInfoController;

    @Test
    public void testGetPersonInfo() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        ResidentDTO residentDTO = new ResidentDTO(new Person(
                firstName,
                lastName,
                "1 Main St",
                "Culver",
                "00000",
                "000-000-0000",
                "john.doe@email.com",
                30,
                new MedicalRecord(firstName, lastName, "01/01/1900", Collections.emptyList(), Collections.emptyList())
        ));

        when(mockPersonService.getPersonInfo(firstName, lastName)).thenReturn(residentDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("1 Main St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies").isArray());

        verify(mockPersonService).getPersonInfo(firstName, lastName);
    }
}
