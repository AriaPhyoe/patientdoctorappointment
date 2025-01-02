package com.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appointment.model.Patient;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	Patient findByEmail(String email);

	Patient findByEmailAndPassword(String email, String password);

	Patient findByPatientId(Long patientId);
}
