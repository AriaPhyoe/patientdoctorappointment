package com.appointment.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appointment.model.Admin;
import com.appointment.model.Doctor;
import com.appointment.repository.DoctorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorService {
	@Autowired
	private DoctorRepository doctorRepo;
	@Autowired
	PasswordEncoder passwordEncoder;

	public void save(Doctor doctor) {
		doctorRepo.save(doctor);

	}
	public Doctor findByEmail(String email) {
		return doctorRepo.findByEmail(email);
	}
	
	public void disableDoctor(Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
        if (doctor != null) {
        	doctor.setVerified(false);
        	doctorRepo.save(doctor);
        }
    }

	public void deleteDoctor(Long doctorId) {
		doctorRepo.deleteById(doctorId);
	}

	public List<Doctor> showAll() {
		// TODO Auto-generated method stub
		return doctorRepo.findAll();
	}

	public Doctor findById(Long id) {
		Optional<Doctor> doctor = doctorRepo.findById(id);
		return doctor.orElse(null);
	}

	@Transactional
	public String updateDoctor(long l, Doctor updatedDoctor) throws IOException {
		System.out.println(updatedDoctor.getFirstname() + "   hkjhkjhkj");

		updatedDoctor.setDoctorId(l);

		doctorRepo.save(updatedDoctor);

		return "update successfully";
	}
	public String checklogin(String em, String pass) {
		Doctor doc = doctorRepo.findByEmail(em);
		System.out.print(doc);
		if(doc!=null) {
			if(passwordEncoder.matches(pass, doc.getPassword())) {
				if(doc.isVerified()) {
					pass= doc.getPassword();
					doc = doctorRepo.findByEmailAndPassword(em, pass);
					if(doc!=null) {
						return "redirect:/doctor/main";
					}
				}else {
	                return "doctorlogin";
	            }
				
			}
		}
		return "redirect:/docsys/index";
	}
	public Doctor getDoctorByEmail(String email) {
		// TODO Auto-generated method stub
		return doctorRepo.findByEmail(email);
	}
	public boolean saveDoctor(Doctor doctor) {
		if (doctorRepo.findByEmail(doctor.getEmail()) == null) {
			doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
			doctorRepo.save(doctor);
			return true;
		}
		return false;
	}
	
	public Doctor getAdminById(Long id) {
		// Make sure 'id' is not null before calling findById
		if (id == null) {
			throw new IllegalArgumentException("ID must not be null");
		}
		return doctorRepo.findById(id).orElse(null);
	}
	public Optional<Doctor> getDoctorById1(Long doctorId) {
        return doctorRepo.findById(doctorId);
    }
	public long countDoctors() {
        return doctorRepo.count();
    }

}
