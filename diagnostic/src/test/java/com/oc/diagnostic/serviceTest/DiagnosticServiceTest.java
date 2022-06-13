package com.oc.diagnostic.serviceTest;

import com.oc.diagnostic.model.Assessment;
import com.oc.diagnostic.model.Note;
import com.oc.diagnostic.model.Patient;
import com.oc.diagnostic.proxy.HistoryProxy;
import com.oc.diagnostic.proxy.PatientProxy;
import com.oc.diagnostic.service.impl.DiagnosticServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@SpringBootTest
public class DiagnosticServiceTest {

    private DiagnosticServiceImpl diagnosticService;

    PatientProxy patientProxy = mock(PatientProxy.class);
    HistoryProxy historyProxy = mock(HistoryProxy.class);
    private Patient patient;
    private Note note;

    private List<Note> noteList;

    @BeforeEach
    public void setup() {
        diagnosticService = new DiagnosticServiceImpl(patientProxy, historyProxy);
        LocalDate birthday = LocalDate.of(1983,06,03);
        patient = new Patient();
        patient.setFirstname("firstname");
        patient.setPatientId(1);
        patient.setBirthday(birthday);
        note = new Note();
        note.setPatId(1);
        note.setCommentary("Hémoglobine A1C Microalbumine Taille Poids Fume Anormal Cholestérol Vertige Rechute Réaction Anticorps");
        noteList = new ArrayList<>();
        noteList.add(note);
    }

    @Test
    public void getAssessmentByIdTest() {
        when(patientProxy.getPatientById(1)).thenReturn(Optional.ofNullable(patient));
        when(historyProxy.getPatientNote(1)).thenReturn(noteList);
        assertEquals(diagnosticService.getAssessmentById(1).getRisk(),"Early onset");
    }

    @Test
    public void getAssessmentByFamilyName() {
        when(patientProxy.getPatientByFirstname("firstname")).thenReturn(Optional.ofNullable(patient));
        when(historyProxy.getPatientNote(1)).thenReturn(noteList);
        assertEquals(diagnosticService.getAssessmentByFamilyName("firstname").getRisk(), "Early onset");
    }
}
