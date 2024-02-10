package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.service.PhoneAlertService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PhoneAlertController.class)
public class PhoneAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneAlertService mockPhoneAlertService;

    @InjectMocks
    private PhoneAlertController phoneAlertController;

    @Test
    public void testGetPhoneAlert() throws Exception {
        Integer stationNumber = 0;

        when(mockPhoneAlertService.getPhoneAlert(stationNumber)).thenReturn(Arrays.asList("000-000-0000", "111-111-1111"));

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .param("stationNumber", String.valueOf(stationNumber))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("000-000-0000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("111-111-1111"));

        verify(mockPhoneAlertService).getPhoneAlert(stationNumber);
    }
}
