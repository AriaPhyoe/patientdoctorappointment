package com.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.model.AssignedAppointment;
import com.appointment.model.Doctor;
import com.appointment.repository.AppointmentRepository;
import com.appointment.repository.DoctorRepository;
import com.appointment.repository.AssignedAppointmentRepository;

@Service
public class AssignedAppointmentService {

    @Autowired
    private AssignedAppointmentRepository assignedRepo;

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AppointmentRepository appRep;


	
	 public void saveTimeSlot(LocalDate appointmentDate, LocalTime startingTime,
	LocalTime endingTime, Long doctorId, boolean isBooked, int limit) { Doctor
	 doctor = doctorRepository.findById(doctorId) .orElseThrow(() -> new
	 IllegalArgumentException("Doctor not found"));
	 
	 AssignedAppointment timeSlot = new AssignedAppointment();
	  timeSlot.setAppointmentDate(appointmentDate);
	  timeSlot.setStartingTime(startingTime); timeSlot.setEndingTime(endingTime);
	  timeSlot.setDoctor(doctor); timeSlot.setBooked(isBooked);
	  timeSlot.setLimit(limit); timeSlot.setFull(0); assignedRepo.save(timeSlot); }
	 
    
    
    
    
	public List<AssignedAppointment> getAvailableTimeSlots(Long doctorId) {
	    return assignedRepo.findByDoctor_DoctorIdAndIsBookedFalse(doctorId);
	}

	public AssignedAppointment getTimeSlotsById(long timeSlotId) {
		return assignedRepo.findAllById(timeSlotId);
	}

	public int getAppointmentCount(long timeSlotId) {
        return appRep.countAppointmentByTimeSlotId(timeSlotId);
    }

	public void updateTimeSlot(AssignedAppointment timeSlot) {
		assignedRepo.save(timeSlot);
		
	}

	public void RemoveAppByID(long timeSlotId) {
		assignedRepo.deleteById(timeSlotId);
		
	}


}
