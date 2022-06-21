package com.oc.mediscreen.service.impl;

import com.oc.mediscreen.model.Assessment;
import com.oc.mediscreen.model.Note;
import com.oc.mediscreen.model.Patient;
import com.oc.mediscreen.model.PatientDTO;
import com.oc.mediscreen.proxy.DiagnosticProxy;
import com.oc.mediscreen.proxy.HistoryProxy;
import com.oc.mediscreen.proxy.PatientProxy;
import com.oc.mediscreen.service.MediscreenService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * <b>MediscreenServiceImpl is the class which implement MediscreenService and call proxies to get or send data</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getPatientList</li>
 *         <li>getPatientById</li>
 *         <li>addPatient</li>
 *         <li>updatePatient</li>
 *         <li>deletePatient</li>
 *         <li>getPatientNotes</li>
 *         <li>addPatientNote</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */
@Service
public class MediscreenServiceImpl implements MediscreenService {



    private final PatientProxy patientProxy;
    private final HistoryProxy historyProxy;
    private final DiagnosticProxy diagnosticProxy;


    public MediscreenServiceImpl(PatientProxy patientProxy, HistoryProxy historyProxy, DiagnosticProxy diagnosticProxy) {
        this.patientProxy = patientProxy;
        this.historyProxy = historyProxy;
        this.diagnosticProxy = diagnosticProxy;
    }

    /**
     * Call PatientProxy to get the patient list
     * @return the patient list
     */
    @Override
    public List<Patient> getPatientList() {
        List<Patient> patientList = (List<Patient>) patientProxy.getPatientList();
        return patientList;
    }

    /**
     * Call the PatientProxy to get a patient by his id
     * @param id id of the patient
     * @return the patient
     */
    @Override
    public Optional<Patient> getPatientById(int id) {
        return patientProxy.getPatientById(id);
    }

    /**
     * Call the PatientProxy to add a new patient
     * @param family variable of patient object
     * @param given variable of patient object
     * @param dob variable of patient object
     * @param sex variable of patient object
     * @param address variable of patient object
     * @param phone variable of patient object
     * @return the patient
     */
    @Override
    public Patient addPatient(String family, String given, LocalDate dob, String sex, String address, String phone) {
        String birthday = dob.toString();
        return patientProxy.addPatient(family,given,birthday,sex,address,phone);
    }

    /**
     * Call PatientProxy to update a patient
     * @param id id of the patient
     * @param patient patient to update
     * @return the patient
     */
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

    /**
     * Call PatientProxy to delete a patient by his id
     * @param id id of the patient
     */
    @Override
    public void deletePatient(int id) {
        patientProxy.deletePatient(id);
    }

    /**
     * Call HistoryProxy to get the list of patient's notes
     * @param patId the patId of notes
     * @return the list of notes
     */
    @Override
    public List<Note> getPatientNotes(int patId){
        return historyProxy.getPatientNote(patId);
    }

    /**
     * Call the HistoryProxy to add a note to a patient
     * @param patId the patId of the note
     * @param note the note to add
     * @return the note
     */
    @Override
    public Note addPatientNote(int patId, Note note) {
        String patientNote = note.getCommentary();
        return historyProxy.addNote(patId,patientNote);
    }

    /**
     * Call the DiagnosticProxy to get patient's assessment
     * @param id id of the patient
     * @return the patient's assessment
     */
    @Override
    public Assessment getPatientAssessment(int id) {
        Assessment patientAssessment = diagnosticProxy.getAssessmentById(id);
        return patientAssessment;
    }

    /**
     * Call PatientProxy and DiagnosticProxy to get the patientDTO information with his diabetes assessment
     * @param id id of the patient
     * @return the patientDTO
     */
    public PatientDTO getPatientAssessmentRiskById(int id) {
        Patient patient = patientProxy.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        Assessment patientAssessment = diagnosticProxy.getAssessmentById(id);
        LocalDate today = LocalDate.now();
        LocalDate patientBirthday = patient.getBirthday();
        long age = ChronoUnit.YEARS.between(patientBirthday,today);
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstname(patient.getFirstname());
        patientDTO.setLastname(patient.getLastname());
        patientDTO.setAge(age);
        patientDTO.setRisk(patientAssessment.getRisk());
        return patientDTO;
    }

    /**
     * Call diagnosticProxy to get the patient's diabetes assessment by his firstname
     * @param firstname firtname of the patient
     * @return patient's assessment
     */
    @Override
    public Assessment getPatientAssessmentByFirstname(String firstname) {
        Assessment patientAssessment = diagnosticProxy.getAssessmentByFamilyName(firstname);
        return patientAssessment;
    }
}
