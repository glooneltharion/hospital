package com.glooneltharion.hospital.repositories;

import com.glooneltharion.hospital.models.NurseNote;
import com.glooneltharion.hospital.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NurseNoteRepository extends JpaRepository<NurseNote, Long> {

    List<NurseNote> findByPatient(Patient patient);
}
