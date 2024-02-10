package com.safetyNet.safetyNetAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.service.MedicalRecordService;
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

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService mockMedicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testFind() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecord mockMedicalRecord = MedicalRecord.builder().firstName(firstName).lastName(lastName).birthdate("01/01/1900").build();

        when(mockMedicalRecordService.find(anyString(), anyString())).thenReturn(mockMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.get("/medicalRecord")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(firstName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(lastName));
    }

    @Test
    public void testFindAll() throws Exception {
        List<MedicalRecord> mockMedicalRecordList = Arrays.asList(
                MedicalRecord.builder().firstName("John").lastName("Doe").birthdate("01/01/1900").build(),
                MedicalRecord.builder().firstName("Jane").lastName("Doe").birthdate("01/01/1898").build()
        );

        when(mockMedicalRecordService.findAll()).thenReturn(mockMedicalRecordList);

        mockMvc.perform(MockMvcRequestBuilders.get("/medicalRecord/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockMedicalRecordList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].birthdate").value("01/01/1898"));
    }

    @Test
    public void testCreate() throws Exception {
        MedicalRecord newMockMedicalRecord = MedicalRecord.builder().firstName("John").lastName("Doe").birthdate("01/01/1900").build();
        String newMockMedicalRecordToJson = mapper.writeValueAsString(newMockMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMockMedicalRecordToJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockMedicalRecordService).create(newMockMedicalRecord);
    }

    @Test
    public void testCreates() throws Exception {
        List<MedicalRecord> newMockMedicalRecordList = Arrays.asList(
                MedicalRecord.builder().firstName("John").lastName("Doe").birthdate("01/01/1900").build(),
                MedicalRecord.builder().firstName("Jane").lastName("Doe").birthdate("01/01/1898").build()
        );

        String newMockMedicalRecordListToJson = mapper.writeValueAsString(newMockMedicalRecordList);

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newMockMedicalRecordListToJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockMedicalRecordService).create(newMockMedicalRecordList);
    }

    @Test
    public void testUpdate() throws Exception {
        MedicalRecord updatedMockMedicalRecord = MedicalRecord.builder().firstName("John").lastName("Doe").birthdate("01/01/1900").build();
        String updatedMockMedicalRecordToJson = mapper.writeValueAsString(updatedMockMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedMockMedicalRecordToJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockMedicalRecordService).update(updatedMockMedicalRecord);
    }

    @Test
    public void testDelete() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockMedicalRecordService).delete(firstName, lastName);
    }
}
