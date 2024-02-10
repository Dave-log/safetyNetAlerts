package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import com.safetyNet.safetyNetAlerts.service.impl.MedicalRecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @Mock
    private MedicalRecordRepository mockMedicalRecordRepository;

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @Test
    public void testFind() {
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecord expectedMedicalRecord = MedicalRecord.builder().firstName(firstName).lastName(lastName).build();

        when(mockMedicalRecordRepository.find(firstName, lastName)).thenReturn(expectedMedicalRecord);

        MedicalRecord result = medicalRecordService.find(firstName, lastName);

        assertEquals(expectedMedicalRecord, result);
    }

    @Test
    public void testFindAll() {
        List<MedicalRecord> expectedMedicalRecords = List.of(
                MedicalRecord.builder().firstName("John").lastName("Doe").build(),
                MedicalRecord.builder().firstName("Jane").lastName("Doe").build()
        );
        when(mockMedicalRecordRepository.findAll()).thenReturn(expectedMedicalRecords);

        List<MedicalRecord> result = medicalRecordService.findAll();

        assertEquals(expectedMedicalRecords.size(), result.size());
        assertEquals(expectedMedicalRecords, result);
    }

    @Test
    public void testCreate() {
        MedicalRecord medicalRecord = MedicalRecord.builder().firstName("John").lastName("Doe").build();

        medicalRecordService.create(medicalRecord);

        verify(mockMedicalRecordRepository).save(medicalRecord);
    }

    @Test
    public void testCreates() {
        List<MedicalRecord> medicalRecordList = List.of(
                MedicalRecord.builder().firstName("John").lastName("Doe").build(),
                MedicalRecord.builder().firstName("Jane").lastName("Doe").build()
        );

        medicalRecordService.create(medicalRecordList);

        verify(mockMedicalRecordRepository).saveAll(medicalRecordList);
    }

    @Test
    public void testUpdate_MedicalRecordExists() {
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecord medicalRecord = MedicalRecord.builder().firstName(firstName).lastName(lastName).build();
        when(mockMedicalRecordRepository.find(firstName, lastName)).thenReturn(medicalRecord);

        medicalRecordService.update(medicalRecord);

        verify(mockMedicalRecordRepository, times(1)).update(medicalRecord);
    }

    @Test
    public void testUpdate_MedicalRecordNotExists() {
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecord medicalRecord = MedicalRecord.builder().firstName(firstName).lastName(lastName).build();
        when(mockMedicalRecordRepository.find(firstName, lastName)).thenReturn(null);

        medicalRecordService.update(medicalRecord);

        verify(mockMedicalRecordRepository, never()).update(medicalRecord);
    }

    @Test
    public void testDelete_MedicalRecordExists() {
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecord medicalRecord = MedicalRecord.builder().firstName(firstName).lastName(lastName).build();
        when(mockMedicalRecordRepository.find(firstName, lastName)).thenReturn(medicalRecord);

        medicalRecordService.delete(firstName, lastName);

        verify(mockMedicalRecordRepository, times(1)).delete(medicalRecord);
    }

    @Test
    public void testDelete_MedicalRecordNotExists() {
        String firstName = "John";
        String lastName = "Doe";
        when(mockMedicalRecordRepository.find(firstName, lastName)).thenReturn(null);

        medicalRecordService.delete(firstName, lastName);

        verify(mockMedicalRecordRepository, never()).delete(any());
    }
}
