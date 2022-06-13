package com.oc.history.service.impl;

import com.oc.history.model.Note;
import com.oc.history.repository.HistoryRepository;
import com.oc.history.service.HistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b>HistoryServiceImpl is the class which implement HistoryService and call HistoryRepository to get or send data for and from database</b>
 * <p>
 *     contains methods
 *     <ul>
 *         <li>addNote</li>
 *         <li>getPatientNote</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    private HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    /**
     * Call HistoryRepository to save a new note in database
     * @param note the content of the note
     * @return the note
     */
    @Override
    public Note addNote(Note note) {
        return historyRepository.save(note);
    }

    /**
     * Call HistoryRepository to get the list of notes for a patient
     * @param patId the patient's id
     * @return the list of notes
     */
    @Override
    public List<Note> getPatientNote(int patId) {
        return historyRepository.findPatientNotesByPatId(patId);
    }
}
