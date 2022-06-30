package com.oc.diagnostic.proxy;

import com.oc.diagnostic.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Optional;

@FeignClient( name = "patient", url = "${patient.url}")
public interface PatientProxy {

    @GetMapping(value = "/patient/{id}")
    Optional<Patient> getPatientById(@PathVariable int id);

    @GetMapping(value = "/getpatient/{firstname}")
    Optional<Patient> getPatientByFirstname(@PathVariable String firstname);
}
