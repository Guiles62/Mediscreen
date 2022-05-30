package com.oc.mediscreen.service;

import com.oc.mediscreen.model.Patient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface MediscreenService {

    List<Patient> getPatientList();
    Optional<Patient> getPatientById(@PathVariable int id);
    Patient addPatient(@RequestParam String family, @RequestParam String given, @RequestParam String dob,
                       @RequestParam String sex, @RequestParam String address, @RequestParam String phone);
    Patient updatePatient(@PathVariable("id") int id,@RequestParam String firstname, @RequestParam String lastname, @RequestParam String birthday,
                          @RequestParam String gender, @RequestParam String address, @RequestParam String phone);
    void deletePatient(@PathVariable int id);
}
