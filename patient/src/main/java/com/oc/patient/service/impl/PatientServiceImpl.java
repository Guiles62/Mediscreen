package com.oc.patient.service.impl;

import com.oc.patient.model.Patient;
import com.oc.patient.repository.PatientRepository;
import com.oc.patient.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getPatientList() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> findPatientById(int id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient addPatient(String family, String given, String dob, String sex, String address, String phone) {
        Patient patient = new Patient();
        LocalDate birthday = LocalDate.parse(dob);
        patient.setFirstname(family);
        patient.setLastname(given);
        patient.setBirthday(birthday);
        patient.setGender(sex);
        patient.setAddress(address);
        patient.setPhone(phone);
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }
}
