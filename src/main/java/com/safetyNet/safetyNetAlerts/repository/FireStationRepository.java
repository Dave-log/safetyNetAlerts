package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface FireStationRepository extends JpaRepository<FireStation,Long> {

    Optional<FireStation> findByAddress(String address);
    Optional<List<FireStation>> findByStation(String station);

    //@Modifying
    //@Query("DELETE FROM FireStation f  WHERE CONCAT(f.address, '-', f.station) IN :duplicateFireStations")
    //void deleteDuplicates(@Param("duplicateFireStations")List<String> duplicateFireStations);
}
