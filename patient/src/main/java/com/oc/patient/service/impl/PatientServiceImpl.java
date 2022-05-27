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
    public Patient updatePatient(int id, Patient patient) {
        Patient patient1 = findPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        LocalDate birthday = patient.getBirthday();
        patient1.setFirstname(patient.getFirstname());
        patient1.setLastname(patient.getLastname());
        patient1.setBirthday(birthday);
        patient1.setAddress(patient.getAddress());
        patient1.setGender(patient.getGender());
        patient1.setPhone(patient.getPhone());
        return patientRepository.save(patient1);
    }

    @Override
    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }
}
