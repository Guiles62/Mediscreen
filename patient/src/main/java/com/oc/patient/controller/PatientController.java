package com.oc.patient.controller;

import com.oc.patient.model.Patient;
import com.oc.patient.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

/**
 * <b>PatientController is the class which send and receive data for and from the PatientService</b>
 * <b>
 *     contains methods
 *     <ul>
 *         <li>getPatientList</li>
 *         <li>getPatientById</li>
 *         <li>getPatientByFirstname</li>
 *         <li>addPatient</li>
 *         <li>updatePatient</li>
 *         <li>deletePatient</li>
 *     </ul>
 * </b>
 * @author Guillaume C
 */
@RestController
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    public final static Logger logger = LogManager.getLogger("PatientLogger");

    /**
     * Call PatientService to get the patient list
     * @return the patient list
     */
    @GetMapping(value = "/patient/list")
    public List<Patient> getPatientList() {
        logger.info("Get patient list");
        List<Patient> patientList = patientService.getPatientList();
        return patientList;
    }

    /**
     * Call PatientService to get a patient by his id
     * @param id patient's id
     * @return the patient
     */
    @GetMapping(value = "/patient/{id}")
    public Optional<Patient> getPatientById(@PathVariable("id") int id) {
        logger.info("Get patient by id");
        return patientService.findPatientById(id);
    }

    /**
     * Call PatientService to get a patient by his firstname
     * @param firstname patient's firstname
     * @return the patient
     */
    @GetMapping(value = "/getpatient/{firstname}")
    public Optional<Patient> getPatientByFirstname(@PathVariable String firstname) {
        return patientService.findByPatientFirstname(firstname);
    }

    /**
     * Call PatientService to send patient information
     * @param family patient's firstname
     * @param given patient's lastname
     * @param dob patient's birthdate
     * @param sex patient's sex
     * @param address patient's address
     * @param phone  patient's phone
     * @return the patient
     */
    @PostMapping(value = "/patient/add")
    public Patient addPatient(@RequestParam String family, @RequestParam String given, @RequestParam String dob,
                              @RequestParam String sex, @RequestParam String address, @RequestParam String phone) {
        logger.info("add a patient");
        LocalDate birthday = LocalDate.parse(dob);
        return patientService.addPatient(family,given,birthday,sex,address,phone);
    }

    /**
     * Call PatientService to update a patient
     * @param patient the patient to update
     * @return the patient
     */
    @PostMapping(value = "patient/update")
    public Patient updatePatient(@RequestBody Patient patient) {
        logger.info("update patient");
        return patientService.updatePatient(patient);
    }

    /**
     * Call PatientService to delete a patient by his id
     * @param id patient's id
     */
    @GetMapping(value = "/patient/delete/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        logger.info("delete patient");
        Patient patient = patientService.findPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        patientService.deletePatient(patient);
    }
}
