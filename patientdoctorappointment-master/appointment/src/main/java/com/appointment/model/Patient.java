package com.appointment.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long patientId;
	private String firstname;
	private String lastname;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String contactNumber;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String gender;
	private int age;
	@Column(nullable = true)
	private LocalDate  dob;
	@Column(nullable = true)
	private String medicalHistory;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Patient(String firstname, String lastname, String email, String contactNumber, String password,
			String address, String gender, int age, LocalDate dob, String medicalHistory) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.contactNumber = contactNumber;
		this.password = password;
		this.address = address;
		this.gender = gender;
		this.age = age;
		this.dob = dob;
		this.medicalHistory = medicalHistory;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	@Override
	public String toString() {
	    return "Patient{" +
	            "patientId=" + patientId +
	            ", firstname='" + firstname + '\'' +
	            ", lastname='" + lastname + '\'' +
	            ", email='" + email + '\'' +
	            ", contactNumber='" + contactNumber + '\'' +
	            ", password='" + password + '\'' +
	            ", address='" + address + '\'' +
	            ", gender='" + gender + '\'' +
	            ", age=" + age +
	            ", dob=" + dob +
	            ", medicalHistory='" + medicalHistory + '\'' +
	            '}';
	}


	

}
