package com.oc.history.controller;

import com.oc.history.model.Note;
import com.oc.history.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>HistoryController is the class which call and receive data from and for HistoryService</b>
 * <p>
 *     contains methods
 *     <ul>
 *         <li>addNote</li>
 *         <li>getPatientNote</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */
@RestController
public class HistoryController {

    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * Call HistoryService to send a new note
     * @param patId patient's id
     * @param e the content of the new note
     * @return the note
     */
    @PostMapping(value = "patHistory/add")
    public Note addNote(@RequestParam int patId, @RequestParam String e) {
        Note note = new Note();
        note.setCommentary(e);
        note.setPatId(patId);
        return historyService.addNote(note);
    }

    /**
     * Call HistoryService to get the list of patient notes
     * @param patId patient's id
     * @return the notes list
     */
    @GetMapping(value = "patHistory/{patId}")
    public List<Note> getPatientNote(@PathVariable("patId") int patId){
        return historyService.getPatientNote(patId);
    }

}
