package com.oc.patient.controller;

import com.oc.patient.model.Patient;
import com.oc.patient.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        List<Patient> patientList = patientService.getPatientList();
        return patientList;
    }


    @GetMapping(value = "/patient/{id}")
    public Optional<Patient> getPatientById(@PathVariable("id") int id) {
        logger.info("Get patient by id");
        return patientService.findPatientById(id);
    }

    @GetMapping(value = "/getpatient/{firstname}")
    public Optional<Patient> getPatientByFirstname(@PathVariable String firstname) {
        return patientService.findByPatientFirstname(firstname);
    }

    @PostMapping(value = "/patient/add")
    public Patient addPatient(@RequestParam String family, @RequestParam String given, @RequestParam LocalDate dob,
                              @RequestParam String sex, @RequestParam String address, @RequestParam String phone) {
        logger.info("add a patient");
        return patientService.addPatient(family,given,dob,sex,address,phone);
    }

    @PostMapping(value = "patient/update")
    public Patient updatePatient(@RequestBody Patient patient) {
        logger.info("update patient");
        return patientService.updatePatient(patient);
    }

    @GetMapping(value = "/patient/delete/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        logger.info("delete patient");
        Patient patient = patientService.findPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        patientService.deletePatient(patient);
    }
}
