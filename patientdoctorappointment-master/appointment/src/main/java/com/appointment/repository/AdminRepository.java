package com.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appointment.model.Admin;

@Repository

public interface AdminRepository extends JpaRepository<Admin, Long> {
	List<Admin> findByFirstnameAndLastname(String firstname, String lastname);

	Admin findByEmailAndPassword(String email, String password);

	Admin findByEmail(String email);


	Admin findByAdminId(Long adminId);
	
	Admin findByFirstname(String firstname);

}
