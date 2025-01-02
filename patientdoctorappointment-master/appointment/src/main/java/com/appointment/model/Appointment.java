package com.appointment.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long appointmentId;

	@ManyToOne
	@JoinColumn(name = "patientId", nullable = false) // Foreign key to Patient entity
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "doctorId", nullable = false) // Foreign key to Doctor entity
	private Doctor doctor;

	@ManyToOne
	@JoinColumn(name = "assignedId", nullable = false)
	private AssignedAppointment assignedAppointment;

	private String status; // e.g., Scheduled, Confirmed, Cancelled
	
	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Appointment(long appointmentId, Patient patient, Doctor doctor, AssignedAppointment assignedAppointment,
			String status) {
		super();
		this.appointmentId = appointmentId;
		this.patient = patient;
		this.doctor = doctor;
		this.assignedAppointment = assignedAppointment;
		this.status = status;
		
	}
	public long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public AssignedAppointment getAssignedAppointment() {
		return assignedAppointment;
	}
	public void setAssignedAppointment(AssignedAppointment assignedAppointment) {
		this.assignedAppointment = assignedAppointment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getAppointmentDate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
