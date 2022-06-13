package com.oc.patient.repository;

import com.oc.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <b>PatientRepository is an interface which extends JpaRepository and send or receive data from and for database</b>
 * <p>
 *     contains method
 *     <ul>
 *         <li>findByFirstname</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByFirstname(String firstname);
}
