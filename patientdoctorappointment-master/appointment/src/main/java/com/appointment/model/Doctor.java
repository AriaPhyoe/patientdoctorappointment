package com.appointment.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.text.NumberFormat;
import java.util.Locale;
@Entity
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long doctorId;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String contactNumber;
	private String specialty;
	private int yearsOfExperience;
	private double consultationFee;
	private String description;
	private String profile;
	private boolean isVerified = false;
	
	@OneToMany(mappedBy="doctor",cascade = CascadeType.ALL)
	private List<AssignedAppointment> assigned = new ArrayList<>();
	
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doctor(long doctorId, String firstname, String lastname, String email, String password, String contactNumber,
			String specialty, int yearsOfExperience, int consultationFee, String description, String profile,
			boolean isVerified, List<AssignedAppointment> assigned) {
		super();
		this.doctorId = doctorId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.contactNumber = contactNumber;
		this.specialty = specialty;
		this.yearsOfExperience = yearsOfExperience;
		this.consultationFee = consultationFee;
		this.description = description;
		this.profile = profile;
		this.isVerified = isVerified;
		this.assigned = assigned;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	

	public double getConsultationFee() {
		return consultationFee;
	}

	public void setConsultationFee(double consultationFee) {
		this.consultationFee = consultationFee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public List<AssignedAppointment> getAssigned() {
		return assigned;
	}

	public void setAssigned(List<AssignedAppointment> assigned) {
		this.assigned = assigned;
	}
	
	public String getFormattedConsultationFee() {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        return formatter.format(consultationFee);
    }
	
	 @Override
	    public String toString() {
	        return "Doctor [doctorId=" + doctorId + ", firstname=" + firstname + ", lastname=" + lastname + ", email="
	                + email + ", contactNumber=" + contactNumber + ", specialty=" + specialty + ", yearsOfExperience="
	                + yearsOfExperience + ", consultationFee=" + getFormattedConsultationFee() + ", description="
	                + description + ", profile=" + profile + ", isVerified=" + isVerified + "]";
	    }
	
	
	

	

}
