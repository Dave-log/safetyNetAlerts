package com.safetyNet.safetyNetAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.safetyNetAlerts.dto.FireStationDTO;
import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService mockFireStationService;

    @InjectMocks
    private FireStationController fireStationController;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGetPersonsCoveredByStation() throws Exception {
        Integer stationNumber = 0;

        List<Person> mockPersonsCoveredByStation = new ArrayList<>();
        mockPersonsCoveredByStation.add(Person.builder().firstName("John").lastName("Doe").address("1 Main St").city("Culver").build());
        mockPersonsCoveredByStation.add(Person.builder().firstName("Jane").lastName("Doe").address("2 Second St").city("Culver").build());

        FireStationDTO expectedDTO = new FireStationDTO(mockPersonsCoveredByStation, 2, 0);

        when(mockFireStationService.getPersonsCoveredByStationsSortedByAge(anyInt())).thenReturn(expectedDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .param("stationNumber", String.valueOf(stationNumber))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personsCoveredByStation").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfAdults").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfChildren").value(0));
    }

    @Test
    public void testFindAll() throws Exception {
        List<FireStation> mockFireStationList = Arrays.asList(
                FireStation.builder().stationNumber(5).addresses(Collections.emptySet()).build(),
                FireStation.builder().stationNumber(6).addresses(Collections.emptySet()).build()
        );

        when(mockFireStationService.findAll()).thenReturn(mockFireStationList);

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockFireStationList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].station").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].station").value(6));
    }

    @Test
    public void testCreate() throws Exception {
        FireStation newMockFireStation = FireStation.builder().stationNumber(0).addresses(Collections.emptySet()).build();
        String newMockFireStationToJson = mapper.writeValueAsString(newMockFireStation);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMockFireStationToJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockFireStationService).create(newMockFireStation);
    }

    @Test
    public void testCreates() throws Exception {
        List<FireStation> newMockFireStationList = Arrays.asList(
                FireStation.builder().stationNumber(5).addresses(Collections.emptySet()).build(),
                FireStation.builder().stationNumber(6).addresses(Collections.emptySet()).build()
        );
        String newMockFireStationListToJson = mapper.writeValueAsString(newMockFireStationList);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMockFireStationListToJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockFireStationService).creates(newMockFireStationList);
    }

    @Test
    public void testUpdate() throws Exception {
        Integer stationNumber = 0;
        String address = "1 Main St";

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .param("address", address)
                .param("newStationNumber", String.valueOf(stationNumber))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockFireStationService).update(address, stationNumber);
    }

    @Test
    public void testDeleteFireStationByAddress() throws Exception {
        Integer stationNumber = 0;
        String address = "1 Main St";

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/deleteByAddress")
                .param("stationNumber", String.valueOf(stationNumber))
                .param("address", address)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockFireStationService).deleteAddress(stationNumber, address);
    }

    @Test
    public void testDeleteFireStationByStation() throws Exception {
        Integer stationNumber = 0;

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/deleteByStation")
                .param("stationNumber", String.valueOf(stationNumber))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockFireStationService).deleteFireStation(stationNumber);
    }

}
