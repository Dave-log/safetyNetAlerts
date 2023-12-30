package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecord,Long> {}
