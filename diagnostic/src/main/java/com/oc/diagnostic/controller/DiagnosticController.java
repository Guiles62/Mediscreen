package com.oc.diagnostic.controller;

import com.oc.diagnostic.model.Assessment;
import com.oc.diagnostic.service.DiagnosticService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>DiagnosticController is the class which call and receive data for and from DiagnosticService</b>
 * <p>
 *     contains method
 *     <ul>
 *         <li>getAssessmentById</li>
 *         <li>getAssessmentByFamilyName</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */
@RestController
public class DiagnosticController {

    private DiagnosticService diagnosticService;

    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    /**
     * Call DiagnosticService to generate Patient assessment with patient's id
     * @param id id of the patient
     * @return patient's assessment
     */
    @PostMapping(value = "/assess/{id}")
    public Assessment getAssessmentById(@PathVariable("id") int id) {
        Assessment assessment = diagnosticService.getAssessmentById(id);
        return assessment;
    }
}
