package com.oc.patient.serviceTest;

import com.oc.patient.model.Patient;
import com.oc.patient.repository.PatientRepository;
import com.oc.patient.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientServiceTest {

    private PatientServiceImpl patientService;

    @MockBean
    private PatientRepository patientRepository;

    @InjectMocks
    private Patient patient = mock(Patient.class);

    private List<Patient>patientList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        patientService = new PatientServiceImpl(patientRepository);
        patient = new Patient();
        patient.setPatientId(1);
        patient.setAddress("1234");
        patient.setLastname("lastname");
        patient.setFirstname("firstname");
        patient.setGender("M");
        patient.setPhone("123456");
        patient.setBirthday(LocalDate.of(1983,06,03));
        patientList.add(patient);
    }

    @Test
    public void getPatientListTest() {
        Mockito.when(patientRepository.findAll()).thenReturn(patientList);
        assertTrue(patientService.getPatientList().size() == 1);
    }

    @Test
    public void findPatientByIdTest() {
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.ofNullable(patient));
        assertEquals(patient.getPatientId(), patientService.findPatientById(1).get().getPatientId());
    }

    @Test
    public void findByPatientFirstnameTest() {
        Mockito.when(patientRepository.findByFirstname("firstname")).thenReturn(Optional.ofNullable(patient));
        assertEquals(patient.getPatientId(), patientService.findByPatientFirstname("firstname").get().getPatientId());
    }

    @Test
    public void addPatientTest() {
        patientService.addPatient("family","given",LocalDate.of(1983,06,03),"M","address","phone");
        verify(patientRepository,times(1)).save(any(Patient.class));
    }

    @Test
    public void updatePatientTest() {
        Mockito.when(patientRepository.save(patient)).thenReturn(patient);
        patientService.updatePatient(patient);
        verify(patientRepository,times(1)).save(patient);
    }

    @Test
    public void deletePatientTest() {
        patientService.deletePatient(patient);
        verify(patientRepository,times(1)).delete(patient);
    }
}
