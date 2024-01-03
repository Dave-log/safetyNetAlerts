package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/list")
    public List<MedicalRecord> list() {
        return medicalRecordService.getMedicalRecords();
    }

    @PostMapping("/add")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PostMapping("/addlist")
    public List<MedicalRecord> addMedicalRecords(@RequestBody List<MedicalRecord> listMedicalRecords) {
        return medicalRecordService.createMedicalRecords(listMedicalRecords);
    }

    @PutMapping("/update")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/delete")
    public MedicalRecord deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        return medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

}
