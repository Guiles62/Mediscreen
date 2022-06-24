package com.oc.mediscreen.controller;


import com.oc.mediscreen.model.Assessment;
import com.oc.mediscreen.model.Note;
import com.oc.mediscreen.model.Patient;
import com.oc.mediscreen.model.PatientDTO;
import com.oc.mediscreen.service.MediscreenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 *<b>MediscreenController is the class which call and receive data for and from the template</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getPatientList</li>
 *         <li>getPatientById</li>
 *         <li>getPatientForm</li>
 *         <li>updatePatient</li>
 *         <li>addPatientForm</li>
 *         <li>addPatient</li>
 *         <li>deletePatient</li>
 *         <li>getPatientNotes</li>
 *         <li>addPatientNote</li>
 *         <li>addPatientForm</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Controller
public class MediscreenController {

    private MediscreenService mediscreenService;

    public MediscreenController(MediscreenService mediscreenService) {
        this.mediscreenService = mediscreenService;
    }

    private final static Logger logger = LogManager.getLogger("BidListController");

    /**
     * Call MediscreenService to get the patient list
     * @param model the list of patient to use in template
     * @return the template patient/list
     */
    @GetMapping(value = "/patient/list")
    public String getPatientList(Model model) {
        logger.info("get the list of patient");
        List<Patient> patientList = mediscreenService.getPatientList();
        model.addAttribute("patients", patientList);
        return "patient/list";
    }

    /**
     * Call MediscreenService to get the informations of a patient
     * @param id id of the patient
     * @param model the patient and the list of note about him
     * @return the patient/view template
     */
    @GetMapping(value = "/patient/{id}")
    public String getPatientById(@PathVariable ("id") int id, Model model) {
        logger.info("Get patient by id");
        Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);
        List<Note> notes = mediscreenService.getPatientNotes(id);
        model.addAttribute("notes", notes);
        Assessment assessmentById = mediscreenService.getPatientAssessment(id);
        model.addAttribute("assessment", assessmentById);
        return "patient/view";
    }

    /**
     * Call MediscreenService to get the patient we want to update
     * @param id id of the patient
     * @param model the patient we want to update for template use
     * @return the patient/update template
     */
    @GetMapping(value = "/patient/update/{id}")
    public String getPatientForm(@PathVariable("id") int id, Model model) {
        logger.info("get the form to update patient informations");
        Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        patient.setBirthday(null);
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    /**
     * Validate if patient object is correct and call MediscreenService to update his information
     * @param id id of the patient
     * @param patientToUpdate the patient with his information update
     * @param result confirmation of the patient information to update are correct
     * @param model the patient
     * @return if result is correct, return the patient/view template otherwise return to the patient/update template
     */
    @PostMapping(value = "/patient/update/{id}")
    public String updatePatient(@PathVariable("id") int id,@Valid  Patient patientToUpdate,BindingResult result, Model model) {
        logger.info("Validate Patient sent to controller and call service to save it");
        if(!result.hasErrors()) {
            mediscreenService.updatePatient(id, patientToUpdate);
            Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            model.addAttribute("patient", patient);
            List<Note> notes = mediscreenService.getPatientNotes(id);
            model.addAttribute("notes", notes);
            Assessment assessmentById = mediscreenService.getPatientAssessment(id);
            model.addAttribute("assessment", assessmentById);
            return "patient/view";
        }
        return "patient/update";
    }

    /**
     * Get the template with the form to add a patient
     * @param patient the patient object
     * @return the patient/add template
     */
    @GetMapping(value = "/patient/add")
    public String addPatientForm(Patient patient) {
        logger.info("get the form to add a patient");
        return "patient/add";
    }

    /**
     * validate the patient object and call MediscreenService to add him
     * @param patient the patient to add
     * @param result the result of patient validation
     * @param model the patient list
     * @return if the patient is correct return patient/list template otherwise, return patient/add template
     */
    @PostMapping(value = "/patient/validate")
    public String addPatient(@Valid Patient patient, BindingResult result, Model model) {
        logger.info("Validate patient to add it in database");
        if(!result.hasErrors()) {
            String firstname = patient.getFirstname();
            String lastname = patient.getLastname();
            LocalDate birthday = patient.getBirthday();
            String gender = patient.getGender();
            String address = patient.getAddress();
            String phone = patient.getPhone();
            mediscreenService.addPatient(firstname, lastname, birthday, gender, address, phone);
            model.addAttribute("patients", mediscreenService.getPatientList());
            return "patient/list";
        }
        return "patient/add";
    }

    /**
     * Call MediscreenService to delete a patient
     * @param id id of the patient to delete
     * @param model the list of patients
     * @return the patient/list template
     */
    @GetMapping(value = "/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, Model model) {
        logger.info("Delete the patient by id");
        mediscreenService.deletePatient(id);
        model.addAttribute("patients", mediscreenService.getPatientList());
        return "patient/list";
    }

    /**
     * Call MediscreenService to get patient notes
     * @param patId id of the patient which get notes
     * @param model the list of patient notes
     * @return the patient/view template
     */
    @GetMapping(value = "/patHistory/{patId}")
    public String getPatientNotes(@PathVariable("patId") int patId, Model model) {
        logger.info("get the patient notes");
        List<Note> patientNotes = mediscreenService.getPatientNotes(patId);
        model.addAttribute("patientNotes", patientNotes);
        return "patient/view";
    }

    /**
     * Validate note object send from the form and call MediscreenService to add them
     * @param id id of the patient we want to add notes
     * @param note note we want to add
     * @param result result of note validation
     * @param model the list of patient notes and the affiliated patient
     * @return if result is correct return the patient/view template otherwise return patHistory/add template
     */
    @PostMapping(value = "/patHistory/add/{id}")
    public String addPatientNote(@PathVariable("id") int id, @Valid Note note, BindingResult result, Model model){
        if(result.hasErrors()){
            return "patHistory/add";
        }
        mediscreenService.addPatientNote(id, note);
        Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);
        List<Note> notes = mediscreenService.getPatientNotes(id);
        model.addAttribute("notes", notes);
        Assessment assessmentById = mediscreenService.getPatientAssessment(id);
        model.addAttribute("assessment", assessmentById);
        return "patient/view";
    }

    /**
     * Get the template to have the form to add a new note
     * @param id id of the patient
     * @param note the note object to add
     * @param model the patient affiliated
     * @return the patHistory/add template
     */
    @GetMapping(value = "/patHistory/add/{id}")
    public String addPatientNoteForm(@PathVariable("id") int id,Note note, Model model){
        logger.info("get the form to add a Note");
        Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);
        return "patHistory/add";
    }

    /**
     * Call mediscreenService to get patientDTO and patient informations for the assessment template
     * @param id id of the patient
     * @param model the patientDTO and patient affiliated
     * @return the assessment/view template
     */
    @GetMapping(value = "/assess/{id}")
    public String getPatientAssessmentById(@PathVariable("id") int id, Model model) {
        logger.info("Get the patient assessment");
        PatientDTO patientDTO = mediscreenService.getPatientAssessmentRiskById(id);
        model.addAttribute("patientdto", patientDTO);
        Patient patient = mediscreenService.getPatientById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);
        return "/assessment/view";
    }
}
