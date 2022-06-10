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

@Service
public class DiagnosticServiceImpl implements DiagnosticService {

    private final PatientProxy patientProxy;
    private final HistoryProxy historyProxy;

    public DiagnosticServiceImpl(PatientProxy patientProxy, HistoryProxy historyProxy) {
        this.patientProxy = patientProxy;
        this.historyProxy = historyProxy;
    }


    @Override
    public Assessment addAssessment(int id) {
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
}
