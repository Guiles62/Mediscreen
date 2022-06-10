package com.oc.diagnostic.controller;

import com.oc.diagnostic.model.Assessment;
import com.oc.diagnostic.service.DiagnosticService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticController {

    private DiagnosticService diagnosticService;

    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    @PostMapping(value = "/assess/{id}")
    public Assessment getAssessment(@PathVariable("id") int id) {
        Assessment assessment = diagnosticService.addAssessment(id);
        return assessment;
    }
}
