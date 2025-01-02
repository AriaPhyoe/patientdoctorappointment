package com.appointment.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appointment.model.AssignedAppointment;
import com.appointment.model.Doctor;

@Repository
public interface AssignedAppointmentRepository extends JpaRepository<AssignedAppointment, Long> {

	List<AssignedAppointment> findByDoctor_DoctorIdAndIsBookedFalse(Long doctorId);

	AssignedAppointment findAllById(long timeSlotId);

	Optional<AssignedAppointment> findByDoctorAndAppointmentDateAndStartingTime(Doctor doctor,
			LocalDate appointmentDate, LocalTime startingTime);

}
