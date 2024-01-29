package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.repository.impl.MedicalRecordRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordRepositoryTest {

    @InjectMocks
    private MedicalRecordRepositoryImpl medicalRecordRepository;

    @Mock
    private Map<Pair<String, String>, MedicalRecord> mockMedicalRecordMap;

    @Test
    public void testFind_MedicalRecordExists() {
        MedicalRecord mockMedicalRecord = new MedicalRecord(
                "Roger",
                "Boyd",
                "09/06/2017",
                Collections.emptyList(),
                Collections.emptyList()
        );

        when(mockMedicalRecordMap.get(any())).thenReturn(mockMedicalRecord);

        MedicalRecord realMedicalRecord = medicalRecordRepository.find("Roger", "Boyd");

        assertEquals(mockMedicalRecord, realMedicalRecord);
    }

    @Test
    public void testFind_MedicalRecordNotExists() {
        when(mockMedicalRecordMap.get(any())).thenReturn(null);

        MedicalRecord medicalRecord = medicalRecordRepository.find("John", "Doe");

        assertNull(medicalRecord);
    }

    @Test
    public void testFindAll_Success() {
        List<MedicalRecord> mockMedicalRecordList = new ArrayList<>();
        mockMedicalRecordList.add(MedicalRecord.builder().firstName("John").lastName("Doe").birthdate("01/01/1900").build());
        mockMedicalRecordList.add(MedicalRecord.builder().firstName("Jane").lastName("Doe").birthdate("01/01/1905").build());

        when(mockMedicalRecordMap.values()).thenReturn(mockMedicalRecordList);

        List<MedicalRecord> medicalRecordList = medicalRecordRepository.findAll();

        assertEquals(mockMedicalRecordList, medicalRecordList);
    }

    @Test
    public void testFindAll_EmptyList() {
        List<MedicalRecord> emptyList = new ArrayList<>();

        when(mockMedicalRecordMap.values()).thenReturn(emptyList);

        List<MedicalRecord> result = medicalRecordRepository.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void testSave_Success() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecord medicalRecordToSave = new MedicalRecord(
                firstName,
                lastName,
                "01/01/1900",
                Collections.emptyList(),
                Collections.emptyList()
        );

        when(mockMedicalRecordMap.put(Pair.of(firstName, lastName), medicalRecordToSave)).thenReturn(null);

        medicalRecordRepository.save(medicalRecordToSave);

        verify(mockMedicalRecordMap).put(Pair.of(firstName, lastName), medicalRecordToSave);
    }

    @Test
    public void testUpdate_Success() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecord existingMedicalRecord = new MedicalRecord(
                firstName,
                lastName,
                "old birthdate",
                Arrays.asList("o","l","d"),
                Arrays.asList("o","l","d")
        );

        MedicalRecord updatedMedicalRecord = new MedicalRecord(
                firstName,
                lastName,
                "new birthdate",
                Arrays.asList("n","e","w"),
                Arrays.asList("n","e","w")
        );

        when(mockMedicalRecordMap.get(Pair.of(firstName, lastName))).thenReturn(existingMedicalRecord);

        medicalRecordRepository.update(updatedMedicalRecord);

        verify(mockMedicalRecordMap).get(Pair.of(firstName, lastName));

        assertEquals("new birthdate", existingMedicalRecord.getBirthdate());
        assertEquals(Arrays.asList("n","e","w"), existingMedicalRecord.getMedications());
        assertEquals(Arrays.asList("n","e","w"), existingMedicalRecord.getAllergies());
    }

    @Test
    public void testDelete_Success() {
        String firstName = "John";
        String lastName = "Doe";

        MedicalRecord medicalRecordToDelete = MedicalRecord.builder().firstName(firstName).lastName(lastName).birthdate("01/01/1900").build();

        when(mockMedicalRecordMap.remove(Pair.of(firstName, lastName))).thenReturn(medicalRecordToDelete);

        medicalRecordRepository.delete(medicalRecordToDelete);

        verify(mockMedicalRecordMap).remove(Pair.of(firstName, lastName));
    }
}
