package com.appointment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long adminId;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;
	private String contactNumber;

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(String firstname, String lastname,String username, String email, String password, String contactNumber) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username=username;
		this.email = email;
		this.password = password;
		this.contactNumber = contactNumber;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
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
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
	    return "Admin{" +
	            "adminId=" + adminId +
	            ", firstname='" + firstname + '\'' +
	            ", lastname='" + lastname + '\'' +
	            ", username='" + username + '\'' +
	            ", email='" + email + '\'' +
	            ", password='" + password + '\'' +
	            ", contactNumber='" + contactNumber + '\'' +
	            '}';
	}

	
}