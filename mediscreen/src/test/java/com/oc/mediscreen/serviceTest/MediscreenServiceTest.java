package com.oc.mediscreen.serviceTest;

import com.oc.mediscreen.model.Assessment;
import com.oc.mediscreen.model.Note;
import com.oc.mediscreen.model.Patient;
import com.oc.mediscreen.proxy.DiagnosticProxy;
import com.oc.mediscreen.proxy.HistoryProxy;
import com.oc.mediscreen.proxy.PatientProxy;
import com.oc.mediscreen.service.impl.MediscreenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MediscreenServiceTest {

    private MediscreenServiceImpl mediscreenService;
    private PatientProxy patientProxy = mock(PatientProxy.class);
    private HistoryProxy historyProxy = mock(HistoryProxy.class);
    private DiagnosticProxy diagnosticProxy = mock(DiagnosticProxy.class);

    @InjectMocks
    private Assessment assessment;
    @InjectMocks
    private Note note;
    @InjectMocks
    private Patient patient;

    List<Patient> patientList = new ArrayList<>();
    List<Note> noteList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        mediscreenService = new MediscreenServiceImpl(patientProxy,historyProxy,diagnosticProxy);
        assessment = new Assessment();
        note = new Note();
        note.setCommentary("abcdef");
        patient = new Patient();
        patientList.add(patient);
        noteList.add(note);
    }

    @Test
    public void getPatientListTest() {
        when(patientProxy.getPatientList()).thenReturn(patientList);
        mediscreenService.getPatientList();
        verify(patientProxy,times(1)).getPatientList();
    }

    @Test
    public void getPatientByIdTest() {
        when(patientProxy.getPatientById(1)).thenReturn(Optional.ofNullable(patient));
        mediscreenService.getPatientById(1);
        verify(patientProxy,times(1)).getPatientById(1);
    }

    @Test
    public void addPatientTest() {
        when(patientProxy.addPatient("a","b", "1983-06-03","m","c","d")).thenReturn(patient);
        mediscreenService.addPatient("a","b", LocalDate.of(1983,06,03),"m","c","d");
        verify(patientProxy,times(1)).addPatient("a","b", "1983-06-03","m","c","d");
    }

    @Test
    public void updatePatientTest() {
        when(patientProxy.updatePatient(patient)).thenReturn(patient);
        when(patientProxy.getPatientById(1)).thenReturn(Optional.ofNullable(patient));
        mediscreenService.updatePatient(1,patient);
        verify(patientProxy,times(1)).updatePatient(patient);
    }

    @Test
    public void deletePatientTest() {
        mediscreenService.deletePatient(1);
        verify(patientProxy,times(1)).deletePatient(1);
    }

    @Test
    public void getPatientNotesTest() {
        when(historyProxy.getPatientNote(1)).thenReturn(noteList);
        mediscreenService.getPatientNotes(1);
        verify(historyProxy,times(1)).getPatientNote(1);
    }

    @Test
    public void addPatientNoteTest() {
        when(historyProxy.addNote(1,"abcdef")).thenReturn(note);
        mediscreenService.addPatientNote(1,note);
        verify(historyProxy,times(1)).addNote(1,"abcdef");
    }

    @Test
    public void getPatientAssessmentTest() {
        when(diagnosticProxy.getAssessmentById(1)).thenReturn(assessment);
        mediscreenService.getPatientAssessment(1);
        verify(diagnosticProxy,times(1)).getAssessmentById(1);
    }

    @Test
    public void getPatientAssessmentByFirstnameTest() {
        when(diagnosticProxy.getAssessmentByFamilyName("a")).thenReturn(assessment);
        mediscreenService.getPatientAssessmentByFirstname("a");
        verify(diagnosticProxy,times(1)).getAssessmentByFamilyName("a");
    }
}
