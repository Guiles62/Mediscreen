package com.oc.history.service.impl;

import com.oc.history.model.Note;
import com.oc.history.repository.HistoryRepository;
import com.oc.history.service.HistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public Note addNote(Note note) {
        return historyRepository.save(note);
    }

    @Override
    public List<Note> getPatientNote(int patId) {
        return historyRepository.findPatientNotesByPatId(patId);
    }
}
