package com.oc.mediscreen.proxy;

import com.oc.mediscreen.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@FeignClient( name = "patient", url = "localhost:8081")
public interface PatientProxy {

    @GetMapping(value = "/patient/list")
    List<Patient> getPatientList();

    @GetMapping(value = "/patient/{id}")
    Optional<Patient> getPatientById(@PathVariable int id);

    @PostMapping(value = "/patient/add")
    Patient addPatient(@RequestParam String family, @RequestParam String given, @RequestParam LocalDate dob,
                       @RequestParam String sex, @RequestParam String address, @RequestParam String phone);

    @PostMapping(value = "patient/update")
    Patient updatePatient(@RequestBody Patient patient);

    @GetMapping(value = "/patient/delete/{id}")
    void deletePatient(@PathVariable int id);

}
