package com.appointment.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appointment.model.Patient;
import com.appointment.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientService {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private PatientRepository patientRepository;

	public void savePatient(Patient patient) {
		patientRepository.save(patient);
	}
	public boolean savePatient1(Patient patient) {
		if (patientRepository.findByEmail(patient.getEmail()) == null) {
			patient.setPassword(passwordEncoder.encode(patient.getPassword()));
			patientRepository.save(patient);
			return true;
		}
		return false;
	}

	public Patient findByEmail(String email) {
		return patientRepository.findByEmail(email);
	}

	public void save(Patient patient) {
		patientRepository.save(patient);
	}

	public List<Patient> showAll() {
		// TODO Auto-generated method stub
		return patientRepository.findAll();
	}

	public void deletePatient(Long patientId) {
		patientRepository.deleteById(patientId);
	}

	public Patient findById(Long id) {
		Optional<Patient> patient = patientRepository.findById(id);
		return patient.orElse(null);
	}

	@Transactional
	public String updatePatient(long l, Patient updatedPatient) throws IOException {
		System.out.println(updatedPatient.getFirstname() + "   hkjhkjhkj");

		updatedPatient.setPatientId(l);

		patientRepository.save(updatedPatient);

		return "update successfully";
	}
	public String checklogin(String email, String password) {
		Patient patient = patientRepository.findByEmail(email);
		if (patient != null) {
			if (passwordEncoder.matches(password, patient.getPassword()))
				;
			password = patient.getPassword();
			patient = patientRepository.findByEmailAndPassword(email, password);
			if (patient == null) {
				return "patientLogin";
			}

		}
		return "redirect:/patient/main";
	}

	public Patient getPatientByEmail(String email) {
		// TODO Auto-generated method stub
		return patientRepository.findByEmail(email);
	}
	public Optional<Patient> getPatientById1(Long patientId) {
		// TODO Auto-generated method stub
		return patientRepository.findById(patientId);
	}
	public Patient getPatientById(Long PatientId) {
		return patientRepository.findByPatientId(PatientId);
	}
	public List<Patient> getAllPatients() {
        return patientRepository.findAll(); // Fetch all patients from the repository
    }
	public long countPatients() {
        return patientRepository.count();
    }

}
