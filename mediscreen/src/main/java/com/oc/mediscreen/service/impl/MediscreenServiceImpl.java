package com.oc.mediscreen.service.impl;

import com.oc.mediscreen.model.Patient;
import com.oc.mediscreen.proxy.PatientProxy;
import com.oc.mediscreen.service.MediscreenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediscreenServiceImpl implements MediscreenService {



    private PatientProxy patientProxy;


    public MediscreenServiceImpl(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    public MediscreenServiceImpl() {
    }

    @Override
    public List<Patient> getPatientList() {
        List<Patient> patientList = patientProxy.getPatientList();
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
    public Patient updatePatient(int id) {
        return patientProxy.updatePatient(id);
    }

    @Override
    public void deletePatient(int id) {
        patientProxy.deletePatient(id);
    }
}
