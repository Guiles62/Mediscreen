package com.oc.history.controller;

import com.oc.history.model.Note;
import com.oc.history.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "patHistory/{patId}")
    public List<Note> getPatientNote(@PathVariable("patId") int patId){
        return historyService.getPatientNote(patId);
    }

}
