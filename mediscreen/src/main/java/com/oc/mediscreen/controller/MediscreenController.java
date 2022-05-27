package com.oc.mediscreen.controller;

import com.oc.mediscreen.model.Patient;
import com.oc.mediscreen.service.MediscreenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}
