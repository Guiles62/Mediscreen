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
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping(value = "/patient/update/{id}")
    public String updatePatient(@PathVariable("id") int id, @Valid Patient patient, BindingResult result, Model model) {
        logger.info("Validate Patient sent to controller and call service to save it");
        if (!result.hasErrors()) {
            mediscreenService.updatePatient(id,patient);
            return "patient/view";
        }
        return "patient/update";
    }
}
