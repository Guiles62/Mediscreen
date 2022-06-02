package com.oc.history.service;

import com.oc.history.model.Note;

import java.util.List;

public interface HistoryService {

    Note addNote(Note note);
    List<Note> getPatientNote(int patId);
}
