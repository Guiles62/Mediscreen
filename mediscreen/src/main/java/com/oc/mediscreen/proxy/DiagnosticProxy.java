package com.oc.mediscreen.proxy;

import com.oc.mediscreen.model.Assessment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient( name = "diagnostic", url = "localhost:8083")
public interface DiagnosticProxy {

    @PostMapping(value = "/assess/{id}")
    Assessment getAssessmentById(@PathVariable("id") int id);

    @PostMapping(value = "assessment/{firstname}")
    Assessment getAssessmentByFamilyName(@PathVariable("firstname") String firstname);
}
