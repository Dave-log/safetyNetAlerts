package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.dto.FloodDTO;
import com.safetyNet.safetyNetAlerts.dto.ResidentDTO;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.model.Person;
import com.safetyNet.safetyNetAlerts.service.FloodService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FloodController.class)
public class FloodAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloodService mockFloodService;

    @InjectMocks
    private FloodController floodController;

    @Test
    public void testGetFloodInfo() throws Exception {
        when(mockFloodService.getFloodInfoByStations(anyList())).thenReturn(createFloodDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .param("stations", "1", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.residentsByAddress['1 Main St'].length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.residentsByAddress['1 Main St'][0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.residentsByAddress['2 Second St'].length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.residentsByAddress['2 Second St'][0].name").value("Alice Smith"));

        verify(mockFloodService).getFloodInfoByStations(Arrays.asList(1, 2));
    }

    private FloodDTO createFloodDTO() {
        Map<String, List<ResidentDTO>> residentsByAddressMap = new HashMap<>();
        List<ResidentDTO> residentDTOList1 = Arrays.asList(
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
        List<ResidentDTO> residentDTOList2 = List.of(
                new ResidentDTO(new Person(
                        "Alice",
                        "Smith",
                        "2 Second St",
                        "Culver",
                        "00000",
                        "000-000-0000",
                        "alice.smith@email.com",
                        28,
                        new MedicalRecord("Alice", "Smith", "01/01/1900", Collections.emptyList(), Collections.emptyList())
                ))
        );

        residentsByAddressMap.put("1 Main St", residentDTOList1);
        residentsByAddressMap.put("2 Second St", residentDTOList2);

        return new FloodDTO(residentsByAddressMap);
    }
}
