package com.oc.mediscreen.service.impl;

import com.oc.mediscreen.model.Patient;
import com.oc.mediscreen.proxy.PatientProxy;
import com.oc.mediscreen.service.MediscreenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediscreenServiceImpl implements MediscreenService {



    private final PatientProxy patientProxy;


    public MediscreenServiceImpl(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }


    @Override
    public List<Patient> getPatientList() {
        List<Patient> patientList = (List<Patient>) patientProxy.getPatientList();
        return patientList;
    }

    @Override
    public Optional<Patient> getPatientById(int id) {
        return patientProxy.getPatientById(id);
    }

    @Override
    public Patient addPatient(String family, String given, String dob, String sex, String address, String phone) {
        return patientProxy.addPatient(family,given,dob,sex,address,phone);
    }

    @Override
    public Patient updatePatient(int id, Patient patient) {
        Patient patientForUpdate = patientProxy.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        patientForUpdate.setFirstname(patient.getFirstname());
        patientForUpdate.setLastname(patient.getLastname());
        patientForUpdate.setAddress(patient.getAddress());
        patientForUpdate.setGender(patient.getGender());
        patientForUpdate.setPhone(patient.getPhone());
        return patientProxy.updatePatient(patientForUpdate);
    }

    @Override
    public void deletePatient(int id) {
        patientProxy.deletePatient(id);
    }
}
