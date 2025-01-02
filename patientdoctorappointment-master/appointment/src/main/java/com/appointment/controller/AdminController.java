package com.appointment.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appointment.model.Admin;
import com.appointment.model.Appointment;
import com.appointment.model.Doctor;
import com.appointment.model.Patient;
import com.appointment.service.AdminService;
import com.appointment.service.AppointmentService;
import com.appointment.service.DoctorService;
import com.appointment.service.PatientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private AdminService adminSer;

	@Autowired
	private PatientService patientSer;

	@Autowired
	private DoctorService doctorSer;

	@Autowired
	private AppointmentService appointmentSer;

//	===================Admin Log In===========================

	@GetMapping("/login") // request name or endpoint
	public String startPage() {

		return "adminlogin"; // index.html view page
	}

	@PostMapping("/loginprocessAdmin")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession httpSession) {
		String result = adminSer.checklogin(email, password);
		if ("redirect:/admin/main".equals(result)) {
			Admin admin = adminSer.getAdminByEmail(email);
			if (admin != null) {

				httpSession.setAttribute("adminEmail", admin.getEmail());
				httpSession.setAttribute("adminId", admin.getAdminId());
				return "redirect:/admin/main"; // Redirect to index or appropriate page
			}
		} else {
			model.addAttribute("loginError", "Invalid email or password");
		}
		return "adminLogin";
	}

//	=================================================================

//	===================Admin Sign Up===========================

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminSignup";
	}

	@PostMapping("/signUpprocess")
	public String addProcess(@ModelAttribute("admin") Admin a) {
		adminSer.saveAdmin(a);
		return "redirect:/admin/signup";
	}

	@PostMapping("/registerprocessAdmin")
	public String saveAdmin(@ModelAttribute("admin") Admin admin, Model model) {
		if (adminSer.getAdminByEmail(admin.getEmail()) != null) {
			model.addAttribute("registerError", "This email is already registered.");
			return "adminSignup";
		}
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		adminSer.save(admin);
		return "redirect:/admin/login";
	}

	@GetMapping("/main") // request name or endpoint
	public String mainPage(HttpSession httpSession, Model model) {
		Long adminId = (Long) httpSession.getAttribute("adminId");

		if (adminId == null) {
			return "redirect:/admin/login";
		}
		Admin admin = adminSer.findById(adminId);
		if (admin != null) {
			model.addAttribute("admin", admin);
			List<Patient> patient = patientSer.showAll();
			model.addAttribute("patients", patient);
//    		model.addAttribute("admin", adminSer.showAll());
			long appointmentsCount = appointmentSer.countAppointments();
	        long doctorsCount = doctorSer.countDoctors();
	        long patientsCount = patientSer.countPatients();

		    model.addAttribute("appointmentsCount", appointmentsCount);
		    model.addAttribute("doctorsCount", doctorsCount);
		    model.addAttribute("patientsCount", patientsCount);
		}

		return "mainPageAdmin"; // index.html view page
	}

// =================================================================
//	===================Register Patient===========================
	@GetMapping("/registerPatient")
	public String registerDelivery(Model model) {
		model.addAttribute("patient", new Patient());
		return "patientRegisterByAdmin";
	}

	@PostMapping("/registerprocessPatient")
	public String registerProcess(@ModelAttribute("patient") Patient patient, Model model) {
		// Check if the email is already in use
		if (patientSer.findByEmail(patient.getEmail()) != null) {
			model.addAttribute("registerError", "Email already in use.");
			return "patientRegisterByAdmin"; // Return to the registration page with an error message
		}

		// Encode the password before saving it
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));

		// Log the patient details for debugging
		System.out.println("Saving patient: " + patient);

		// Save the patient to the database
		patientSer.save(patient);

		// Add success message and redirect to a confirmation page or the main page
		model.addAttribute("registerSuccess", "Registration successful!");
		return "redirect:/admin/main"; // Redirect to the main page after successful registration
	}

	// =================================================================
	// ===================View Patient===========================
	@GetMapping("/showPatient")
	public String showPatient(Model model) {
		List<Patient> patient = patientSer.showAll();
		model.addAttribute("patients", patient);
		return "patientviewByAdmin";
	}
	// =================================================================
	// ===================update & delete Patient===========================

	@GetMapping("/editPatient/{id}")
	public String editPatient(@PathVariable("id") long patientId, Model model) {
		try {
			// Retrieve the patient details
			Patient patient = patientSer.findById(patientId);
			model.addAttribute("patient", patient);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Failed to load patient details: " + e.getMessage());
			return "redirect:/admin/showPatient"; // Redirect to patient list if an error occurs
		}
		return "editPatientRegisterByAdmin"; // Load the edit form template
	}

	@PostMapping("/updatePatient/{id}")
	public String updatePatient(@PathVariable("id") long patientId, @ModelAttribute("patient") Patient patient,
			RedirectAttributes redirectAttributes) {
		try {
			patientSer.updatePatient(patientId, patient);
			// redirectAttributes.addFlashAttribute("successMessage", "Patient updated
			// successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to update patient: " + e.getMessage());
		}

		return "redirect:/admin/showPatient"; // Redirect to patient detail page after update
	}

	@GetMapping("/patientDelete/{id}")
	public String deletePatient(@PathVariable Long id) {
		patientSer.deletePatient(id);
		return "redirect:/admin/showPatient";
	}

	// =================================================================
	// ===================Register Doctor===========================

	@GetMapping("/registerDoctor")
	public String registerDoctor(Model model) {
		model.addAttribute("doctor", new Doctor());
		return "doctorRegisterByAdmin";
	}

	@PostMapping("/registerprocessDoctor")
	public String saveAdmin(@ModelAttribute("doctor") Doctor doctor, Model model) {
		if (doctorSer.getDoctorByEmail(doctor.getEmail()) != null) {
			model.addAttribute("registerError", "This email is already registered.");
			return "doctorSignup";
		}
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
		doctorSer.save(doctor);
		return "redirect:/admin/main";
	}

	// =================================================================
	// ===================View Doctor===========================
	@GetMapping("/showDoctor")
	public String showDoctor(Model model) {
		List<Doctor> doctor = doctorSer.showAll();
		model.addAttribute("doctors", doctor);
		return "doctorviewByAdmin";
	}

	@PostMapping("/confirmrequest/{id}")
	public String confirmDoctorRequest(@PathVariable("id") long docid) {
		Doctor doctor = doctorSer.findById(docid);
		if (doctor != null) {
			doctor.setVerified(true);
			doctorSer.save(doctor);
		}
		return "redirect:/admin/showDoctor";
	}

	@PostMapping("/doctorDelete/{id}")
	public String deleteDoctor(@PathVariable("id") Long id) {
		Doctor doctor = doctorSer.findById(id);
		if (doctor != null) {
			doctor.setVerified(false);
			doctorSer.deleteDoctor(id);
		}

		return "redirect:/admin/showDoctor";
	}

	// =================================================================
	// ===================update & delete Patient===========================

//		@GetMapping("/editDoctor/{id}")
//		public String editDoctor(@PathVariable("id") long doctorId, Model model) {
//		    try {
//		        // Retrieve the patient details
//		        Doctor doctor = doctorSer.findById(doctorId);
//		        model.addAttribute("doctor", doctor);
//		    } catch (Exception e) {
//		        model.addAttribute("errorMessage", "Failed to load doctor details: " + e.getMessage());
//		        return "redirect:/admin/showDoctor"; // Redirect to patient list if an error occurs
//		    }
//		    return "editdoctorRegisterByAdmin"; // Load the edit form template
//		}
//		
//		@PostMapping("/updateDoctor/{id}")
//		public String updateDoctor(
//		        @PathVariable("id") long doctorId,
//		        @ModelAttribute("doctor") Doctor doctor,
//		        RedirectAttributes redirectAttributes) {
//		    try {
//		        doctorSer.updateDoctor(doctorId, doctor);
//		      //  redirectAttributes.addFlashAttribute("successMessage", "Doctor updated successfully.");
//		    } catch (Exception e) {
//		        redirectAttributes.addFlashAttribute("errorMessage", "Failed to update doctor: " + e.getMessage());
//		    }
//
//		    return "redirect:/admin/showDoctor"; // Redirect to patient detail page after update
//		}

	// =================================================================
	// ===================Show Appointment===========================

	@GetMapping("/showAppointment")
	public String showAppointmentPage(Model model, HttpSession httpSession) {
		Long adminId = (Long) httpSession.getAttribute("adminId");
		if (adminId == null) {
			return "redirect:/admin/login";
		}
		Admin admin = adminSer.getAdminById(adminId);
		if (admin == null) {
			return "redirect:/admin/login";
		}
		List<Appointment> applist = appointmentSer.showAll().stream().filter(app -> "Booked".equals(app.getStatus()))
				.collect(Collectors.toList());
		model.addAttribute("appointment", applist);
		model.addAttribute("admin", admin);
		return "appointmentByAdmin";
	}

	@PostMapping("/confirmAppointment/{appointmentId}")
	public String confirmAppointment(@PathVariable("appointmentId") long appointmentId) {
		Appointment appointment = appointmentSer.getAppointmentById(appointmentId);

		if (appointment != null) {
			appointment.setStatus("Pending");
			appointmentSer.saveAppointment(appointment);
		}

		return "redirect:/admin/showAppointment";
	}

	// =================================================================
	// ===================Show Profile===========================

	@GetMapping("/showAdminProfile")
	public String showAdminProfilePage(Model model, HttpSession session) {
		Long adminId = (Long) session.getAttribute("adminId");
		if (adminId != null) {
			// Fetch the specific shop by ID
			Optional<Admin> optionalAdmin = adminSer.getAdminById1(adminId);
			if (optionalAdmin.isPresent()) {
				model.addAttribute("admin", optionalAdmin.get());
			} else {
				model.addAttribute("orderError", "Admin not found for ID: " + adminId);
			}
		} else {
			model.addAttribute("orderError", "Admin ID not found in session.");
		}
		return "editAdminprofile";
	}

	// =================================================================
	// ===================Edit Profile===========================

	@GetMapping("/showFinishedAppointment")
	public String showFinishedAppointment(Model model, HttpSession httpSession) {
		Long adminId = (Long) httpSession.getAttribute("adminId");
		if (adminId == null) {
			return "redirect:/admin/login";
		}
		Admin admin = adminSer.getAdminById(adminId);
		if (admin == null) {
			return "redirect:/admin/login";
		}
		List<Appointment> applist = appointmentSer.showAll();
		model.addAttribute("appointment", applist);
		model.addAttribute("admin", admin);
		return "ShowFinishedAppointment";
	}

}
