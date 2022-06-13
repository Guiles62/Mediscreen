package com.oc.diagnostic.service.impl;

import com.oc.diagnostic.model.Assessment;
import com.oc.diagnostic.model.Note;
import com.oc.diagnostic.model.Patient;
import com.oc.diagnostic.proxy.HistoryProxy;
import com.oc.diagnostic.proxy.PatientProxy;
import com.oc.diagnostic.service.DiagnosticService;
import org.springframework.stereotype.Service;


import java.text.Normalizer;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>DiagnosticServiceImpl is the class which implement DiagnosticService and call proxies to get or send data</b>
 * <p>
 *     contains methods
 *     <ul>
 *         <li>getAssessmentById</li>
 *         <li>getAssessmentByFamilyName</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */
@Service
public class DiagnosticServiceImpl implements DiagnosticService {

    private final PatientProxy patientProxy;
    private final HistoryProxy historyProxy;

    public DiagnosticServiceImpl(PatientProxy patientProxy, HistoryProxy historyProxy) {
        this.patientProxy = patientProxy;
        this.historyProxy = historyProxy;
    }

    /**
     * Call PatientProxy and HistoryProxy to get data to generate the patient's assessment by his id
     * Compare danger terms list to medical notes to generate assessment
     * @param id id of the patient
     * @return patient's assessment
     */
    @Override
    public Assessment getAssessmentById(int id) {
        Assessment patientAssessment = new Assessment();
        String[] terms={"Hémoglobine A1C","Microalbumine","Taille","Poids","Fume","Anormal","Cholestérol","Vertige","Rechute","Réaction","Anticorps"};
        List<String> triggeringTerms = new ArrayList<>();
        for (String term : terms){
            triggeringTerms.add(term);
        }
        Patient patient = patientProxy.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        List<Note> patientNotes = historyProxy.getPatientNote(id);
        LocalDate today = LocalDate.now();
        LocalDate patientBirthday = patient.getBirthday();
        long age = ChronoUnit.YEARS.between(patientBirthday,today);
        String sex = patient.getGender();
        int riskCount = 0;
        for(Note note : patientNotes) {
            String patientNote = note.getCommentary().toLowerCase();
            patientNote = Normalizer.normalize(patientNote, Normalizer.Form.NFD);
            patientNote = patientNote.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            for(String dangerTerm : triggeringTerms){
                String inLowerCase = dangerTerm.toLowerCase();
                inLowerCase = Normalizer.normalize(inLowerCase, Normalizer.Form.NFD);
                inLowerCase = inLowerCase.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                if (patientNote.contains(inLowerCase)){
                    riskCount++;
                }
            }
        }
        if(riskCount >=2 && riskCount <6 && age > 30){
            patientAssessment.setRisk("Borderline");
        } else if (sex == "M" && age < 30 && riskCount == 3 || sex == "F" && age < 30 && riskCount == 4 || age > 30 && riskCount >= 6 && riskCount <8) {
            patientAssessment.setRisk("In Danger");
        } else if (sex == "M" && age < 30 && riskCount == 5 || sex == "F" && age < 30 && riskCount == 7 || age > 30 && riskCount >= 8) {
            patientAssessment.setRisk("Early onset");
        } else {
            patientAssessment.setRisk("None");
        }
        return patientAssessment;
    }

    /**
     * Call PatientProxy and HistoryProxy to get data to generate the patient's assessment by his firstname
     * Compare danger terms list to medical notes to generate assessment
     * @param familyName patient's firstname
     * @return patient's assessment
     */
    @Override
    public Assessment getAssessmentByFamilyName(String familyName) {
        Assessment patientAssessment = new Assessment();
        String[] terms={"Hémoglobine A1C","Microalbumine","Taille","Poids","Fume","Anormal","Cholestérol","Vertige","Rechute","Réaction","Anticorps"};
        List<String> triggeringTerms = new ArrayList<>();
        for (String term : terms){
            triggeringTerms.add(term);
        }
        Patient patient = patientProxy.getPatientByFirstname(familyName).orElseThrow(() -> new IllegalArgumentException("Invalid patient Firstname:" + familyName));
        List<Note> patientNotes = historyProxy.getPatientNote(patient.getPatientId());
        LocalDate today = LocalDate.now();
        LocalDate patientBirthday = patient.getBirthday();
        long age = ChronoUnit.YEARS.between(patientBirthday,today);
        String sex = patient.getGender();
        int riskCount = 0;
        for(Note note : patientNotes) {
            String patientNote = note.getCommentary().toLowerCase();
            patientNote = Normalizer.normalize(patientNote, Normalizer.Form.NFD);
            patientNote = patientNote.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            for(String dangerTerm : triggeringTerms){
                String inLowerCase = dangerTerm.toLowerCase();
                inLowerCase = Normalizer.normalize(inLowerCase, Normalizer.Form.NFD);
                inLowerCase = inLowerCase.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                if (patientNote.contains(inLowerCase)){
                    riskCount++;
                }
            }
        }
        if(riskCount >=2 && riskCount <6 && age > 30){
            patientAssessment.setRisk("Borderline");
        } else if (sex == "M" && age < 30 && riskCount == 3 || sex == "F" && age < 30 && riskCount == 4 || age > 30 && riskCount >= 6 && riskCount <8) {
            patientAssessment.setRisk("In Danger");
        } else if (sex == "M" && age < 30 && riskCount == 5 || sex == "F" && age < 30 && riskCount == 7 || age > 30 && riskCount >= 8) {
            patientAssessment.setRisk("Early onset");
        } else {
            patientAssessment.setRisk("None");
        }
        return patientAssessment;
    }
}
