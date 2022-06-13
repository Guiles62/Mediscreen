package com.oc.patient.service.impl;

import com.oc.patient.model.Patient;
import com.oc.patient.repository.PatientRepository;
import com.oc.patient.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * <b>PatientServiceImpl is the class which implement PatientService and call PatientRepository to get or send data</b>
 * <p>
 *     contains methods
 *     <ul>
 *         <li>getPatientList</li>
 *         <li>findPatientById</li>
 *         <li>findByPatientFirstname</li>
 *         <li>addPatient</li>
 *         <li>updatePatient</li>
 *         <li>deletePatient</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */
@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Call PatientRepository to get the patient list
     * @return a list of patient
     */
    @Override
    public List<Patient> getPatientList() {
        return patientRepository.findAll();
    }

    /**
     * Call PatientRepository to find a patient by his id
     * @param id patient's id
     * @return the patient
     */
    @Override
    public Optional<Patient> findPatientById(int id) {
        return patientRepository.findById(id);
    }

    /**
     * Call PatientRepository to find a patient by his firstname
     * @param firstname patient's firstname
     * @return the patient
     */
    @Override
    public Optional<Patient> findByPatientFirstname(String firstname) {
        return patientRepository.findByFirstname(firstname);
    }

    /**
     * Call PatientRepository to save a new patient in database
     * @param family patient's firstname
     * @param given patient's lastname
     * @param dob patient's birthdate
     * @param sex patient's sex
     * @param address patient's address
     * @param phone patient's phone
     * @return the patient
     */
    @Override
    public Patient addPatient(String family, String given, LocalDate dob, String sex, String address, String phone) {
        Patient patient = new Patient();
        patient.setFirstname(family);
        patient.setLastname(given);
        patient.setBirthday(dob);
        patient.setGender(sex);
        patient.setAddress(address);
        patient.setPhone(phone);
        return patientRepository.save(patient);
    }

    /**
     * Call PatientRepository to update an existing patient
     * @param patient the patient with update information to save
     * @return the patient
     */
    @Override
    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * Call PatientRepository to delete a patient from the database
     * @param patient the patient we want to delete
     */
    @Override
    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }
}
