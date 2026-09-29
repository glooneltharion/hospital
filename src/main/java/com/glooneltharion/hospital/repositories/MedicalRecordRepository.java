package com.glooneltharion.hospital.repositories;

import com.glooneltharion.hospital.models.MedicalRecord;
import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient(Patient patient);
    List<MedicalRecord> findByDoctor(User doctor);
    @Modifying
    @Query("""
        UPDATE MedicalRecord m
        SET m.doctor = null
        WHERE m.doctor.id = :doctorId
    """)
    void clearDoctorFromRecords(@Param("doctorId") Long doctorId);
}
