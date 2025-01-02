package com.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.appointment.model.Appointment;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

	Appointment findByAppointmentId(long appointmentId);

	@Query("SELECT COUNT(a) FROM Appointment a WHERE a.assignedAppointment.id = :appId")
	    int countAppointmentByTimeSlotId(@Param("appId") long timeSlotId);

	List<Appointment> findByDoctor_DoctorId(Long docId);

	List<Appointment> findByPatient_PatientId(Long userId);

}
