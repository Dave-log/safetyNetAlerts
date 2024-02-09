package com.safetyNet.safetyNetAlerts.service.impl;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import com.safetyNet.safetyNetAlerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private static final Logger logger = LogManager.getLogger(MedicalRecordServiceImpl.class);

    @Autowired
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository; 
    }

    @Override
    public MedicalRecord find(String firstName, String lastName) { return medicalRecordRepository.find(firstName, lastName); }

    @Override
    public List<MedicalRecord> findAll() {
        return medicalRecordRepository.findAll();
    }

    @Override
    public void create(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
    }

    public void create(List<MedicalRecord> medicalRecordList) {
        medicalRecordRepository.saveAll(medicalRecordList);
    }

    @Override
    public void update(MedicalRecord medicalRecord) {

        MedicalRecord optionalMedicalRecord = find(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (optionalMedicalRecord != null) {
            medicalRecordRepository.update(medicalRecord);
            logger.info("The medical record of " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " has been updated.");
        } else {
            logger.error("The medical record of " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " does not exist.");
        }
    }

    public void delete(String firstName, String lastName) {

        MedicalRecord optionalMedicalRecord = find(firstName, lastName);
        if (optionalMedicalRecord != null) {
            medicalRecordRepository.delete(optionalMedicalRecord);
            logger.info("The medical record of " + firstName + " " + lastName + " has been deleted from database.");
        } else {
            logger.error("The medical record of " + firstName + " " + lastName + " does not exist.");
        }
    }
}
