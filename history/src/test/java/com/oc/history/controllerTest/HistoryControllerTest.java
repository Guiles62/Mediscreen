package com.oc.history.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.history.controller.HistoryController;
import com.oc.history.model.Note;
import com.oc.history.service.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    private HistoryController historyController;
    @MockBean
    private HistoryService historyService;

    @InjectMocks
    private Note note;

    private List<Note> noteList;

    @BeforeEach
    public void setup() {
        historyController = new HistoryController(historyService);
    }

    @Test
    public void addNoteTest() throws Exception {
        note = new Note();
        note.setCommentary("commentaryTest");
        note.setPatId(4);
        when(historyService.addNote(note)).thenReturn(note);
        mockMvc.perform(post("/patHistory/add?patId=4&e=commentaryTest")).andExpect(status().isOk());
    }

    @Test
    public void getPatientNote() throws Exception {
        note = new Note();
        note.setCommentary("commentaryTest");
        note.setPatId(4);
        noteList = new ArrayList<>();
        noteList.add(note);
        when(historyService.getPatientNote(4)).thenReturn(noteList);
        mockMvc.perform(get("/patHistory/4")).andExpect(status().isOk());
    }
}
