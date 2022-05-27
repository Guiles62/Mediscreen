package com.oc.mediscreen.controller;

import com.oc.mediscreen.service.MediscreenService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediscreenController {

    private MediscreenService mediscreenService;

    public MediscreenController(MediscreenService mediscreenService) {
        this.mediscreenService = mediscreenService;
    }

    @GetMapping(value = "/patient/list")
    public String getPatientList(Model model) {
        model.addAttribute("patients", mediscreenService.getPatientList());
        return"/patient/list";
    }
}
