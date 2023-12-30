package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) { this.medicalRecordRepository = medicalRecordRepository; }

    public Iterable<MedicalRecord> list() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public Iterable<MedicalRecord> saveAll(List<MedicalRecord> listMedicalRecord) {
        return medicalRecordRepository.saveAll(listMedicalRecord);
    }
}
