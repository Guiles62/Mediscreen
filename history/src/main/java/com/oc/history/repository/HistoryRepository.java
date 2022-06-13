package com.oc.history.repository;

import com.oc.history.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>HistoryRepository is an interface which extends MongoRepository and call database to send or receive data</b>
 * <p>
 *     contains method
 *     <ul>
 *         <li>findPatientNotesByPatId</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */
@Repository
public interface HistoryRepository extends MongoRepository<Note, Integer> {

    List<Note> findPatientNotesByPatId(int patId);
}
