package com.oc.patient.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.patient.controller.PatientController;
import com.oc.patient.model.Patient;
import com.oc.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private PatientController patientController;

    @MockBean
    private PatientService patientService;

    @InjectMocks
    private Patient patient;

    private List<Patient> patientList;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeEach
    public void setup() {
        patientController = new PatientController(patientService);
        patient = new Patient();
        patient.setPatientId(1);
        patient.setAddress("1234");
        patient.setLastname("lastname");
        patient.setFirstname("firstname");
        patient.setGender("M");
        patient.setPhone("123456");
        patient.setBirthday(LocalDate.of(1983,06,03));
        patientList = new ArrayList<>();
        patientList.add(patient);
    }

    @Test
    public void getPatientListTest() throws Exception {
        Mockito.when(patientService.getPatientList()).thenReturn(patientList);
        mockMvc.perform(get("/patient/list")).andExpect(status().isOk());
    }

    @Test
    public void getPatientByIdTest() throws Exception {
        Mockito.when(patientService.findPatientById(1)).thenReturn(Optional.ofNullable(patient));
        mockMvc.perform(get("/patient/1")).andExpect(status().isOk());
    }

    @Test
    public void getPatientByFirstnameTest() throws Exception {
        Mockito.when(patientService.findByPatientFirstname("firstname")).thenReturn(Optional.ofNullable(patient));
        mockMvc.perform(get("/getpatient/firstname")).andExpect(status().isOk());
    }

    @Test
    public void addPatientTest() throws Exception {
        LocalDate birthdate = LocalDate.of(1983,06,03);
        Mockito.when(patientService.addPatient("firstname","lastname",birthdate,"M","1234","123456")).thenReturn(patient);
        patientController.addPatient("firstname","lastname",birthdate,"M","1234","123456");
        verify(patientService,times(1)).addPatient("firstname","lastname",birthdate,"M","1234","123456");
    }

    @Test
    public void updatePatient() throws Exception {
        Mockito.when(patientService.updatePatient(patient)).thenReturn(patient);
        patientController.updatePatient(patient);
        verify(patientService,times(1)).updatePatient(patient);
    }

    @Test
    public void deletePatientTest() throws Exception {
        Mockito.when(patientService.findPatientById(1)).thenReturn(Optional.ofNullable(patient));
        mockMvc.perform(get("/patient/delete/1")).andExpect(status().isOk());
    }
}
