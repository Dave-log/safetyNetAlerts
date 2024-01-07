package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService implements EntityService<MedicalRecord> {

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

    @Autowired
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository; 
    }

    @Override
    public Collection<MedicalRecord> findAll() {
        return medicalRecordRepository.findAll();
    }

    @Override
    public void create(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
    }

    public void creates(List<MedicalRecord> listMedicalRecords) {
        medicalRecordRepository.saveAll(listMedicalRecords);
    }

    public MedicalRecord findByFullName(String firstName, String lastName) {
        return medicalRecordRepository.find(firstName, lastName);
    }

    @Override
    public MedicalRecord update(MedicalRecord medicalRecord) {

        MedicalRecord existingMedicalRecord = null;
        MedicalRecord optionalMedicalRecord = findByFullName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (optionalMedicalRecord != null) {
            existingMedicalRecord = optionalMedicalRecord;
            medicalRecordRepository.save(existingMedicalRecord);
            logger.info("The medical record of " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " has been updated.");
        } else {
            logger.error("The medical record of " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " does not exist.");
        }
        return existingMedicalRecord;
    }

    public MedicalRecord delete(String firstName, String lastName) {

        MedicalRecord existingMedicalRecord = null;
        MedicalRecord optionalMedicalRecord = findByFullName(firstName, lastName);
        if (optionalMedicalRecord != null) {
            existingMedicalRecord = optionalMedicalRecord;
            medicalRecordRepository.delete(firstName, lastName);
            logger.info("The medical record of " + firstName + " " + lastName + " has been deleted from database.");
        } else {
            logger.error("The medical record of " + firstName + " " + lastName + " does not exist.");
        }
        return existingMedicalRecord;
    }

    @Override
    public MedicalRecord delete(){ return null; }
}
