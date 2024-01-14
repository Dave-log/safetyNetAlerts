package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecord find(String firstName, String lastName);

    List<MedicalRecord> findAll();

    void create(MedicalRecord medicalRecord);

    void creates(List<MedicalRecord> medicalRecordList);

    void update(MedicalRecord medicalRecord);

    void delete(String firstName, String lastName);
}
