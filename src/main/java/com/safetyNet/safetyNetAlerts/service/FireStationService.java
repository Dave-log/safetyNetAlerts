package com.safetyNet.safetyNetAlerts.service;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import com.safetyNet.safetyNetAlerts.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {

    private FireStationRepository fireStationRepository;

    public FireStationService(FireStationRepository fireStationRepository) { this.fireStationRepository = fireStationRepository; }

    public Iterable<FireStation> list() {
        return fireStationRepository.findAll();
    }

    public FireStation save(FireStation fireStation) {
        return fireStationRepository.save(fireStation);
    }

    public Iterable<FireStation> saveAll(List<FireStation> listFireStation) {
        return fireStationRepository.saveAll(listFireStation);
    }
}
