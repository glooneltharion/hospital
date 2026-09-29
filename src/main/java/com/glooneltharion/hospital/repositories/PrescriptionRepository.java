package com.glooneltharion.hospital.repositories;

import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.Prescription;
import com.glooneltharion.hospital.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatient(Patient patient);
    List<Prescription> findByDoctor(User doctor);
    @Modifying
    @Query("""
        UPDATE Prescription p
        SET p.doctor = null
        WHERE p.doctor.id = :doctorId
    """)
    void clearDoctorFromPrescriptions(@Param("doctorId") Long doctorId);
}
