package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.NurseNote;
import com.glooneltharion.hospital.models.Patient;

import java.util.List;

public interface NurseNoteService {

    NurseNote create(NurseNote note);

    List<NurseNote> getByPatient(Patient patient);

    void delete(Long id);
}
