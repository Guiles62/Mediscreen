package com.oc.diagnostic.service;

import com.oc.diagnostic.model.Assessment;

public interface DiagnosticService {

    Assessment getAssessmentById(int id);
    Assessment getAssessmentByFamilyName(String familyName);

}
