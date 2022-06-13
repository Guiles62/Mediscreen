package com.oc.history.serviceTest;

import com.oc.history.model.Note;
import com.oc.history.repository.HistoryRepository;
import com.oc.history.service.impl.HistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HistoryServiceTest {

    private HistoryServiceImpl historyService;

    @MockBean
    private HistoryRepository historyRepository;

    @InjectMocks
    private Note note;

    private List<Note> noteList;

    @BeforeEach
    public void setup() {
        historyService = new HistoryServiceImpl(historyRepository);
        note = new Note();
        note.setPatId(1);
        note.setCommentary("commentaryTest");
        noteList = new ArrayList<>();
        noteList.add(note);
    }

    @Test
    public void addNoteTest() {
        when(historyRepository.save(note)).thenReturn(note);
        assertTrue(historyService.addNote(note).getCommentary() == "commentaryTest");
    }

    @Test
    public void getPatientNoteTest() {
        when(historyRepository.findPatientNotesByPatId(1)).thenReturn(noteList);
        assertTrue(historyService.getPatientNote(1).size() == 1);
    }
}
