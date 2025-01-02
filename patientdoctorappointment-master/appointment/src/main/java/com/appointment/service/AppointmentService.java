package com.appointment.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.model.Appointment;
import com.appointment.model.AssignedAppointment;
import com.appointment.model.Doctor;
import com.appointment.model.Patient;

import com.appointment.repository.AppointmentRepository;
@Service
public class AppointmentService {
	@Autowired
	private AppointmentRepository appointRepo;
	public List<Appointment> showAll() {
		// TODO Auto-generated method stub
		return appointRepo.findAll();
	}
	
	public Appointment getAppointmentById(long appointmentId) {
		return appointRepo.findByAppointmentId(appointmentId);
	}
	
	public void bookAppointment(AssignedAppointment assignedAppointment, Patient patient) {
	    // Create a new appointment
	    Appointment appointment = new Appointment();
	    appointment.setStatus("Booked");
	    appointment.setAssignedAppointment(assignedAppointment);
	    appointment.setDoctor(assignedAppointment.getDoctor());
	    appointment.setPatient(patient);
	    appointRepo.save(appointment);
	}
	
	public void saveAppointment(Appointment appointment) {
		appointRepo.save(appointment);
		
	}

	
	public List<Appointment> showByDoctorId(Long docId) {
		return appointRepo.findByDoctor_DoctorId(docId);
	}
	
	public List<Appointment> showAllPatientApp(Long userId) {
		return appointRepo.findByPatient_PatientId(userId);
	}

	public long countAppointments() {
        return appointRepo.count();
    }

}
