package com.oc.history.controller;

import com.oc.history.model.Note;
import com.oc.history.model.Patient;
import com.oc.history.service.HistoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryController {

    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping(value = "patHistory/add")
    public Note addNote(@RequestParam int patId, @RequestParam String e) {
        Note note = new Note();
        note.setCommentary(e);
        note.setPatId(patId);
        return historyService.addNote(note);
    }

}
