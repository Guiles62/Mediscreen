package com.oc.mediscreen.controller;

import com.oc.mediscreen.model.Patient;
import com.oc.mediscreen.service.MediscreenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Controller
public class MediscreenController {

    private MediscreenService mediscreenService;

    public MediscreenController(MediscreenService mediscreenService) {
        this.mediscreenService = mediscreenService;
    }

    private final static Logger logger = LogManager.getLogger("BidListController");

    @GetMapping(value = "/patient/list")
    public String getPatientList(Model model) {
        logger.info("get the list of patient");
        List<Patient> patientList = mediscreenService.getPatientList();
        model.addAttribute("patients", patientList);
        return "patient/list";
    }

    @GetMapping(value = "/patient/{id}")
    public String getPatientById(@PathVariable ("id") int id, Model model) {
        logger.info("Get patient by id");
        Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);
        return "patient/view";
    }

    @GetMapping(value = "/patient/update/{id}")
    public String getPatientForm(@PathVariable("id") int id, Model model) {
        logger.info("get the form to update patient informations");
        Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        patient.setBirthday(null);
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping(value = "/patient/update/{id}")
    public String updatePatient(@PathVariable("id") int id,@RequestParam String firstname, @RequestParam String lastname, @RequestParam String birthday,
                                @RequestParam String gender, @RequestParam String address, @RequestParam String phone, Model model) {
        logger.info("Validate Patient sent to controller and call service to save it");
        mediscreenService.updatePatient(id,firstname,lastname,birthday,gender,address,phone);
        Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);
        return "patient/view";
    }

    @GetMapping(value = "/patient/add")
    public String addPatientForm(Patient patient) {
        logger.info("get the form to add a patient");
        return "patient/add";
    }

    @PostMapping(value = "/patient/validate")
    public String addPatient(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String birthday,
                             @RequestParam String gender, @RequestParam String address, @RequestParam String phone, Model model) {
        logger.info("Validate patient to add it in database");
            mediscreenService.addPatient(firstname,lastname,birthday,gender,address,phone);
            model.addAttribute("patients", mediscreenService.getPatientList());
            return "patient/list";
    }

    @GetMapping(value = "patient/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, Model model) {
        logger.info("Delete the patient by id");
        mediscreenService.deletePatient(id);
        model.addAttribute("patients", mediscreenService.getPatientList());
        return "patient/list";
    }
}
