package com.safetyNet.safetyNetAlerts.controller;

import com.safetyNet.safetyNetAlerts.model.MedicalRecord;
import com.safetyNet.safetyNetAlerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public MedicalRecord find(@RequestParam String firstName, @RequestParam String lastName) {
        return medicalRecordService.find(firstName, lastName);
    }

    @GetMapping("/all")
    public List<MedicalRecord> findAll() {
        return medicalRecordService.findAll();
    }

    @PostMapping
    public void create(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.create(medicalRecord);
    }

    @PostMapping("/list")
    public void creates(@RequestBody List<MedicalRecord> listMedicalRecords) {
        medicalRecordService.creates(listMedicalRecords);
    }

    @PutMapping
    public void update(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.update(medicalRecord);
    }

    @DeleteMapping
    public void delete(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.delete(firstName, lastName);
    }

}
