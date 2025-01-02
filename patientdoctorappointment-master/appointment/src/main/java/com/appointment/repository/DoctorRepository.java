package com.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.appointment.model.Doctor;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	Doctor findByEmail(String email);

	Doctor findByEmailAndPassword(String email, String password);
	@Query("SELECT d FROM Doctor d JOIN FETCH d.assigned")
    List<Doctor> findAllWithAssigned();
}
