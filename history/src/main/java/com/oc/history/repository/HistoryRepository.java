package com.oc.history.repository;

import com.oc.history.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends MongoRepository<Note, Integer> {

    List<Note> findPatientNotesByPatId(int patId);
}
