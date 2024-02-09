package com.safetyNet.safetyNetAlerts.repository.impl;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.repository.MedicalRecordRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private static final Logger logger = LogManager.getLogger(MedicalRecordRepositoryImpl.class);

    private final Map<Pair<String, String>, MedicalRecord> medicalRecordMap;

    @Autowired
    public MedicalRecordRepositoryImpl(Map<Pair<String, String>, MedicalRecord> medicalRecordMap) {
        this.medicalRecordMap = medicalRecordMap;
    }

    public MedicalRecord find(String firstName, String lastName) {
        return medicalRecordMap.get(Pair.of(firstName, lastName));
    }

    @Override
    public List<MedicalRecord> findAll() {
        return new ArrayList<>(medicalRecordMap.values());
    }

    @Override
    public void save(MedicalRecord medicalRecord) {
        medicalRecordMap.put(Pair.of(medicalRecord.getFirstName(), medicalRecord.getLastName()), medicalRecord);
    }

    @Override
    public void saveAll(List<MedicalRecord> list) {
        for (MedicalRecord medicalRecord : list) {
            save(medicalRecord);
        }
    }

    @Override
    public void update(MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordToUpdate = find(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (medicalRecordToUpdate != null) {
            medicalRecordToUpdate.setBirthdate(medicalRecord.getBirthdate());
            medicalRecordToUpdate.setMedications(medicalRecord.getMedications());
            medicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());
        } else {
            logger.error("Medical record of " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " does not exist.");
        }
    }

    @Override
    public void delete(MedicalRecord medicalRecord) {
        medicalRecordMap.remove(Pair.of(medicalRecord.getFirstName(), medicalRecord.getLastName()));
    }
}
