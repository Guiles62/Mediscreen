package com.oc.patient.controller;

import com.oc.patient.model.Patient;
import com.oc.patient.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    public final static Logger logger = LogManager.getLogger("PatientLogger");

    @GetMapping(value = "/patient/list")
    public List<Patient> getPatientList() {
        logger.info("Get patient list");
        return patientService.getPatientList();
    }


    @GetMapping(value = "/patient/{id}")
    public Optional<Patient> getPatientById(@PathVariable("id") int id) {
        logger.info("Get patient by id");
        return patientService.findPatientById(id);
    }

    @PostMapping(value = "/patient/add")
    public Patient addPatient(@RequestParam String family, @RequestParam String given, @RequestParam String dob,
                              @RequestParam String sex, @RequestParam String address, @RequestParam String phone) {
        logger.info("add a patient");
        return patientService.addPatient(family,given,dob,sex,address,phone);
    }

    @PostMapping(value = "patient/update/{id}")
    public Patient updatePatient(@PathVariable("id") int id) {
        logger.info("update patient");
        Patient patient = patientService.findPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        return patientService.updatePatient(id, patient);
    }

    @GetMapping(value = "/patient/delete/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        logger.info("delete patient");
        Patient patient = patientService.findPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        patientService.deletePatient(patient);
    }
}
