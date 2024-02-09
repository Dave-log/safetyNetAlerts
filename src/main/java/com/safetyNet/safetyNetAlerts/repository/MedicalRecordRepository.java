package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import java.util.List;

public interface MedicalRecordRepository {

    MedicalRecord find(String firstName, String lastName);

    List<MedicalRecord> findAll();

    void save(MedicalRecord medicalRecord);

    void saveAll(List<MedicalRecord> medicalRecordList);

    void update(MedicalRecord medicalRecord);

    void delete(MedicalRecord medicalRecord);
}
