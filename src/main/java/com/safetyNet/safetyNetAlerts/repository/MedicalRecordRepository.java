package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MedicalRecordRepository implements EntityRepository<MedicalRecord> {

    private final Map<Pair<String, String>, MedicalRecord> medicalRecordMap = new HashMap<>();

    public MedicalRecord find(String firstName, String lastName) {
        return medicalRecordMap.get(Pair.of(firstName, lastName));
    }

    @Override
    public void save(MedicalRecord medicalRecord) {
        medicalRecordMap.put(Pair.of(medicalRecord.getFirstName(), medicalRecord.getLastName()), medicalRecord);
    }

    public void delete(String firstName, String lastName) {
        medicalRecordMap.remove(Pair.of(firstName, lastName));
    }

    @Override
    public void saveAll(Collection<MedicalRecord> list) {
        for (MedicalRecord medicalRecord : list) {
            Pair<String, String> key = Pair.of(medicalRecord.getFirstName(), medicalRecord.getLastName());
            medicalRecordMap.put(key, medicalRecord);
        }
    }

    @Override
    public Collection<MedicalRecord> findAll() {
        return medicalRecordMap.values();
    }

    @Override
    public MedicalRecord find(){ return null; }

    @Override
    public void delete(){}
}
