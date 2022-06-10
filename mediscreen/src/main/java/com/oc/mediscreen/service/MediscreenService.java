package com.oc.mediscreen.service;

import com.oc.mediscreen.model.Assessment;
import com.oc.mediscreen.model.Note;
import com.oc.mediscreen.model.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * <b>MediscreenService is the interface that will be implemented by MediscreenServiceImpl</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getPatientList</li>
 *         <li>getPatientById</li>
 *         <li>addPatient</li>
 *         <li>updatePatient</li>
 *         <li>deletePatient</li>
 *         <li>getPatientNotes</li>
 *         <li>addPatientNote</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

public interface MediscreenService {


    List<Patient> getPatientList();
    Optional<Patient> getPatientById(int id);
    Patient addPatient(String family, String given, LocalDate dob,
                       String sex, String address, String phone);
    Patient updatePatient(int id,Patient patient);
    void deletePatient(int id);
    List<Note> getPatientNotes(int patId);
    Note addPatientNote(int patId, Note note);
    Assessment getPatientAssessment(int id);
    Assessment getPatientAssessmentByFirstname(String firstname);
}
