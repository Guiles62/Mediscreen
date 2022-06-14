package com.oc.mediscreen.controllerTest;

import com.oc.mediscreen.controller.MediscreenController;
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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MediscreenControllerTest {

    @Autowired
    MockMvc mockMvc;
    private MediscreenController mediscreenController;
    @MockBean
    private MediscreenServiceImpl mediscreenService;
    @InjectMocks
    private Patient patient;
    @InjectMocks
    private Assessment assessment;
    @InjectMocks
    private Note note;
    @Mock
    BindingResult result;

    List<Patient> patientList = new ArrayList<>();
    List<Note> noteList = new ArrayList<>();


    @BeforeEach
    public void setup() {
        mediscreenController = new MediscreenController(mediscreenService);
        patient = new Patient();
        assessment = new Assessment();
        note = new Note();
        patientList.add(patient);
        noteList.add(note);
    }

    @Test
    public void getPatientListTest() throws Exception {
        when(mediscreenService.getPatientList()).thenReturn(patientList);
        mockMvc.perform(get("/patient/list")).andExpect(status().isOk());
    }

    @Test
    public void getPatientByIdTest() {
        Model model = new ConcurrentModel();
        when(mediscreenService.getPatientById(1)).thenReturn(Optional.ofNullable(patient));
        when(mediscreenService.getPatientNotes(1)).thenReturn(noteList);
        when(mediscreenService.getPatientAssessment(1)).thenReturn(assessment);
        mediscreenController.getPatientById(1,model);
        verify(mediscreenService,times(1)).getPatientById(1);
    }

    @Test
    public void getPatientFormTest() throws Exception {
        when(mediscreenService.getPatientById(1)).thenReturn(Optional.ofNullable(patient));
        mockMvc.perform(get("/patient/update/1")).andExpect(status().isOk());
    }

    @Test
    public void updatePatientTest() {
        Model model = new ConcurrentModel();
        when(mediscreenService.getPatientById(1)).thenReturn(Optional.ofNullable(patient));
        mediscreenController.updatePatient(1,patient,result,model);
        verify(mediscreenService,times(1)).updatePatient(1,patient);
    }

    @Test
    public void addPatientFormTest() throws Exception {
        mockMvc.perform(get("/patient/add")).andExpect(status().isOk());
    }

    @Test
    public void addPatientTest() throws Exception {
        mockMvc.perform(post("/patient/validate")).andExpect(status().isOk());
    }

    @Test
    public void deletePatientTest() throws Exception {
        mockMvc.perform(get("/patient/delete/1")).andExpect(status().isOk());
    }

    @Test
    public void getPatientNotesTest() {
        Model model = new ConcurrentModel();
        when(mediscreenService.getPatientNotes(1)).thenReturn(noteList);
        mediscreenController.getPatientNotes(1,model);
        verify(mediscreenService,times(1)).getPatientNotes(1);
    }

    @Test
    public void addPatientNoteTest() {
        Model model = new ConcurrentModel();
        when(mediscreenService.getPatientById(1)).thenReturn(Optional.ofNullable(patient));
        when(mediscreenService.getPatientNotes(1)).thenReturn(noteList);
        mediscreenController.addPatientNote(1,note,result,model);
        verify(mediscreenService,times(1)).addPatientNote(1,note);
    }

    @Test
    public void addPatientNoteFormTest() throws Exception {
        when(mediscreenService.getPatientById(1)).thenReturn(Optional.ofNullable(patient));
        mockMvc.perform(get("/patHistory/add/1")).andExpect(status().isOk());
    }

    @Test
    public void getPatientAssessmentByIdTest() {
        Model model = new ConcurrentModel();
        when(mediscreenService.getPatientAssessment(1)).thenReturn(assessment);
        mediscreenController.getPatientAssessmentById(1,model);
        verify(mediscreenService,times(1)).getPatientAssessment(1);
    }

    @Test
    public void getPatientAssessmentByFamilyNameTest() {
        Model model = new ConcurrentModel();
        when(mediscreenService.getPatientAssessmentByFirstname("g")).thenReturn(assessment);
        mediscreenController.getPatientAssessmentByFamilyName("g",model);
        verify(mediscreenService,times(1)).getPatientAssessmentByFirstname("g");
    }
}
