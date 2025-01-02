package com.appointment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class AssignedAppointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "doctorId", nullable = false)
	private Doctor doctor; // Assuming you have a Doctor entity

	@Column(nullable = false)
	private LocalDate appointmentDate;

	@Column(nullable = false)
	private LocalTime startingTime;

	@Column(nullable = false)
	private LocalTime endingTime;
	@Column(name = "`limit`", nullable = false)
	private int limit;
    private int full;

	@Column(nullable = false)
	private boolean isBooked;

	public AssignedAppointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignedAppointment(Long id, Doctor doctor, LocalDate appointmentDate, LocalTime startingTime,
			LocalTime endingTime, int limit, int full, boolean isBooked) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.appointmentDate = appointmentDate;
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		this.limit = limit;
		this.full = full;
		this.isBooked = isBooked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public LocalTime getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(LocalTime startingTime) {
		this.startingTime = startingTime;
	}

	public LocalTime getEndingTime() {
		return endingTime;
	}

	public void setEndingTime(LocalTime endingTime) {
		this.endingTime = endingTime;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getFull() {
		return full;
	}

	public void setFull(int full) {
		this.full = full;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	

	

}
