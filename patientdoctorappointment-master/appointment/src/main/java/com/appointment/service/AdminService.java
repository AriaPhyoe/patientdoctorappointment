package com.appointment.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appointment.model.Admin;
import com.appointment.model.Appointment;
import com.appointment.model.Doctor;
import com.appointment.model.Patient;
import com.appointment.repository.AdminRepository;

import jakarta.transaction.Transactional;
@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void save(Admin admin) {
		adminRepo.save(admin);

	}

	public List<Admin> getAllData() {
		// TODO Auto-generated method stub
		return adminRepo.findAll();
	}

	public Admin getAdminIdEdit(Long adminId) {
		return adminRepo.findByAdminId(adminId);
	}

	public boolean saveAdmin(Admin admin) {
		if (adminRepo.findByEmail(admin.getEmail()) == null) {
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			adminRepo.save(admin);
			return true;
		}
		return false;
	}

	public String checklogin(String email, String password) {
		Admin admin = adminRepo.findByEmail(email);
		if (admin != null) {
			if (passwordEncoder.matches(password, admin.getPassword()))
				;
			password = admin.getPassword();
			admin = adminRepo.findByEmailAndPassword(email, password);
			if (admin == null) {
				return "adminLogin";
			}

		}
		return "redirect:/admin/main";
	}

	public Admin getAdminById(Long id) {
		// Make sure 'id' is not null before calling findById
		if (id == null) {
			throw new IllegalArgumentException("ID must not be null");
		}
		return adminRepo.findById(id).orElse(null);
	}

	public Admin getAdminByEmail(String email) {
		// TODO Auto-generated method stub
		return adminRepo.findByEmail(email);
	}

	public List<Admin> getAllAdminList() {
		// TODO Auto-generated method stub
		return adminRepo.findAll();
	}

	public void updateAdmin(long id, Admin admin) {
		admin.setAdminId(id);
		adminRepo.save(admin);
	}

	public Admin findById(Long id) {
		Optional<Admin> admin = adminRepo.findById(id);
		return admin.orElse(null);
	}
	
	@Transactional
	public String updateAdminPage(long l, Admin updatedAdmin) throws IOException {
		System.out.println(updatedAdmin.getFirstname() + "   hkjhkjhkj");

		updatedAdmin.setAdminId(l);

		adminRepo.save(updatedAdmin);

		return "update successfully";
	}
	
	public List<Admin> showAll() {
		// TODO Auto-generated method stub
		return adminRepo.findAll();
	}
	
	public Optional<Admin> getAdminById1(Long adminId) {
        return adminRepo.findById(adminId);
    }

	
	

}
