package com.oc.mediscreen.service;

import com.oc.mediscreen.model.Note;
import com.oc.mediscreen.model.Patient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MediscreenService {

    List<Patient> getPatientList();
    Optional<Patient> getPatientById(int id);
    Patient addPatient(String family, String given, LocalDate dob,
                       String sex, String address, String phone);
    Patient updatePatient(int id,Patient patient);
    void deletePatient(int id);
    List<Note> getPatientNotes(int patId);
    Note addPatientNote(int patId, Note note);

}
