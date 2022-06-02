package com.oc.mediscreen.service.impl;

import com.oc.mediscreen.model.Note;
import com.oc.mediscreen.model.Patient;
import com.oc.mediscreen.proxy.HistoryProxy;
import com.oc.mediscreen.proxy.PatientProxy;
import com.oc.mediscreen.service.MediscreenService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MediscreenServiceImpl implements MediscreenService {



    private final PatientProxy patientProxy;
    private final HistoryProxy historyProxy;


    public MediscreenServiceImpl(PatientProxy patientProxy, HistoryProxy historyProxy) {
        this.patientProxy = patientProxy;
        this.historyProxy = historyProxy;
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
    public Patient addPatient(String family, String given, LocalDate dob, String sex, String address, String phone) {
        return patientProxy.addPatient(family,given,dob,sex,address,phone);
    }

    @Override
    public Patient updatePatient(int id,Patient patient) {
        Patient patientForUpdate = patientProxy.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        patientForUpdate.setFirstname(patient.getFirstname());
        patientForUpdate.setLastname(patient.getLastname());
        patientForUpdate.setGender(patient.getGender());
        patientForUpdate.setPhone(patient.getPhone());
        patientForUpdate.setAddress(patient.getAddress());
        patientForUpdate.setBirthday(patient.getBirthday());
        return patientProxy.updatePatient(patientForUpdate);
    }

    @Override
    public void deletePatient(int id) {
        patientProxy.deletePatient(id);
    }

    @Override
    public List<Note> getPatientNotes(int patId){
        return historyProxy.getPatientNote(patId);
    }

    @Override
    public Note addPatientNote(int patId, Note note) {
        String patientNote = note.getCommentary();
        return historyProxy.addNote(patId,patientNote);
    }
}
