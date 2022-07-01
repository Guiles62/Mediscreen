package com.oc.mediscreen.proxy;

import com.oc.mediscreen.model.Assessment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "diagnostic", url = "${diagnostic.url}")
public interface DiagnosticProxy {

    @GetMapping(value = "/assess/{id}")
    Assessment getAssessmentById(@PathVariable("id") int id);

}
