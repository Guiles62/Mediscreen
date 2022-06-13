package com.oc.diagnostic.controllerTest;

import com.oc.diagnostic.controller.DiagnosticController;
import com.oc.diagnostic.model.Assessment;
import com.oc.diagnostic.model.Note;
import com.oc.diagnostic.service.DiagnosticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DiagnosticControllerTest {

    private DiagnosticController diagnosticController;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private Note note;

    @InjectMocks
    private Assessment assessment;

    @MockBean
    private DiagnosticService diagnosticService;


    @BeforeEach
    void setup() {
        diagnosticController = new DiagnosticController(diagnosticService);
        assessment = new Assessment();
        assessment.setRisk("testRisk");
    }

    @Test
    public void getAssessmentByIdTest() throws Exception {
        when(diagnosticService.getAssessmentById(1)).thenReturn(assessment);
        mockMvc.perform(post("/assess/1")).andExpect(status().isOk());
    }

    @Test
    public void getAssessmentByFamilyNameTest() throws Exception {
        when(diagnosticService.getAssessmentByFamilyName("familyname")).thenReturn(assessment);
        mockMvc.perform(post("/assessment/familyname")).andExpect(status().isOk());
    }

}
