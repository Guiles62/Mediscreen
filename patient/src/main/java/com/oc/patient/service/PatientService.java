package com.oc.patient.service;

import com.oc.patient.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> getPatientList();
    Optional<Patient> findPatientById(int id);
    Patient addPatient(String family, String given, String dob, String sex, String address, String phone);
    Patient updatePatient(Patient patient);
    void deletePatient(Patient patient);
}
