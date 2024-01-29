package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.dto.FireDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.FireService;
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

@WebMvcTest(controllers = FireController.class)
public class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireService mockFireService;

    @InjectMocks
    private FireController fireController;

    @Test
    public void testGetFireInfo() throws Exception {
        String address = "1 Main St";

        when(mockFireService.getFireInfo(address)).thenReturn(createFireDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .param("address", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.servingFireStations.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servingFireStations[0]").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.residents.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.residents[0].name").value("John Doe"));

        verify(mockFireService).getFireInfo(address);
    }

    private FireDTO createFireDTO() {
        List<Integer> fireStationNumberList = Arrays.asList(1, 2);
        List<ResidentDTO> fireResidents = Arrays.asList(
                new ResidentDTO(new Person(
                        "John",
                        "Doe",
                        "1 Main St",
                        "Culver",
                        "00000",
                        "000-000-0000",
                        "john.doe@email.com",
                        30,
                        new MedicalRecord("John", "Doe", "01/01/1900", Collections.emptyList(), Collections.emptyList())
                )),
                new ResidentDTO(new Person(
                        "Jane",
                        "Doe",
                        "1 Main St",
                        "Culver",
                        "00000",
                        "000-000-0000",
                        "jane.doe@email.com",
                        32,
                        new MedicalRecord("Jane", "Doe", "01/01/1900", Collections.emptyList(), Collections.emptyList())
                ))
        );

        return new FireDTO(fireStationNumberList, fireResidents);
    }
}
