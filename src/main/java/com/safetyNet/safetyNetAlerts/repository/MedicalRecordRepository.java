package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MedicalRecordRepository {

    Map<Pair<String, String>, MedicalRecord> medicalRecordMap = new HashMap<>();

    MedicalRecord find(String firstName, String lastName);

    List<MedicalRecord> findAll();

    void save(MedicalRecord medicalRecord);

    void saveAll(List<MedicalRecord> medicalRecordList);

    void update(MedicalRecord medicalRecord);

    void delete(MedicalRecord medicalRecord);

}
