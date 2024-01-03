package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository; 
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public List<MedicalRecord> createMedicalRecords(List<MedicalRecord> listMedicalRecords) {
        return medicalRecordRepository.saveAll(listMedicalRecords);
    }

    public Optional<MedicalRecord> getMedicalRecord(String firstName, String lastName) {
        return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {

        MedicalRecord existingMedicalRecord = null;
        Optional<MedicalRecord> optionalMedicalRecord = getMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (optionalMedicalRecord.isPresent()) {
            existingMedicalRecord = optionalMedicalRecord.get();
            existingMedicalRecord.setMedications(medicalRecord.getMedications());
            existingMedicalRecord.setAllergies(medicalRecord.getAllergies());
            existingMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
            medicalRecordRepository.save(existingMedicalRecord);
            logger.info("The medical record of " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " has been updated.");
        } else {
            createMedicalRecord(medicalRecord);
            logger.warn("The medical record of " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " does not exist and has been added to the database.");
        }
        return existingMedicalRecord;
    }

    public MedicalRecord deleteMedicalRecord(String firstName, String lastName) {

        MedicalRecord existingMedicalRecord = null;
        Optional<MedicalRecord> optionalMedicalRecord = getMedicalRecord(firstName, lastName);
        if (optionalMedicalRecord.isPresent()) {
            existingMedicalRecord = optionalMedicalRecord.get();
            medicalRecordRepository.delete(existingMedicalRecord);
            logger.info("The medical record of " + firstName + " " + lastName + " has been deleted from database.");
        } else {
            logger.warn("The medical record of " + firstName + " " + lastName + " does not exist.");
        }
        return existingMedicalRecord;
    }
}
