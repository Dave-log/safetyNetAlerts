package com.safetyNet.safetyNetAlerts.controller;

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
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CommunityEmailController.class)
public class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService mockPersonService;

    @InjectMocks
    private CommunityEmailController communityEmailController;

    @Test
    public void testGetCommunityEmails_Success() throws Exception {
        String city = "Culver";
        Set<String> communityEmails = new HashSet<>(Arrays.asList("john.doe@email.com", "jane.doe@email.com"));

        when(mockPersonService.getCommunityEmails(city)).thenReturn(communityEmails);

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .param("city", city)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(communityEmails.size()));

        verify(mockPersonService).getCommunityEmails(city);
    }


}
