package com.glooneltharion.hospital.repositories;

import com.glooneltharion.hospital.models.Appointment;
import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.User;
import com.glooneltharion.hospital.models.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctor(User doctor);

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByStatus(AppointmentStatus status);
    @Modifying
    @Query("""
    UPDATE Appointment a
    SET a.doctor = null
    WHERE a.doctor.id = :doctorId
""")
    void clearDoctorFromAppointments(Long doctorId);
    @Query("""
        SELECT a
        FROM Appointment a
        WHERE a.doctor = :doctor
        AND a.startTime < :endTime
        AND a.endTime > :startTime
    """)
    List<Appointment> findConflictingAppointments(
            User doctor,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
