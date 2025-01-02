package com.appointment.controller;

import java.util.List;
import java.util.Optional;

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

import com.appointment.model.Appointment;
import com.appointment.model.AssignedAppointment;
import com.appointment.model.Doctor;
import com.appointment.model.Patient;

import com.appointment.repository.PatientRepository;
import com.appointment.service.AppointmentService;
import com.appointment.service.AssignedAppointmentService;
import com.appointment.service.DoctorService;
import com.appointment.service.PatientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	PatientService patientSer;
	@Autowired
	DoctorService doctorSer;
	@Autowired
	PatientRepository patientRepo;
	@Autowired
	AssignedAppointmentService assignedSer;
	@Autowired
	private AppointmentService appointmentSer;
	@Autowired
	private HttpSession httpSession;

	@GetMapping("/login") // request name or endpoint
	public String startPage() {

		return "patientlogin"; // index.html view page
	}

	@PostMapping("/loginprocessPatient")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession httpSession) {
		String result = patientSer.checklogin(email, password);
		if ("redirect:/patient/main".equals(result)) {
			Patient patient = patientSer.getPatientByEmail(email);
			if (patient != null) {

				httpSession.setAttribute("patientEmail", patient.getEmail());
				httpSession.setAttribute("patientId", patient.getPatientId());
				return "redirect:/patient/main"; // Redirect to index or appropriate page
			}
		} else {
			model.addAttribute("loginError", "Invalid email or password");
		}
		return "patientLogin";
	}
	
	
//	=================================================================

//	===================Admin Sign Up===========================

	@GetMapping("/signuppage")
	public String signupPage(Model model) {
		model.addAttribute("patient", new Patient());
		return "patientSignup";
	}

	@PostMapping("/signUpprocess")
	public String addProcess(@ModelAttribute("patient") Patient p) {
		patientSer.savePatient1(p);
		return "redirect:/patient/signuppage";
	}

	@PostMapping("/registerprocessPatient")
	public String registerprocessPatient(@ModelAttribute("patient") Patient patient) {
		boolean isSaved = patientSer.savePatient1(patient);

		// If registration is successful, redirect to the login page
		if (isSaved) {
			return "redirect:/patient/login";
		}
		// If registration fails, remain on the registration page
		return "patientSignup";
	}

	@GetMapping("/main") // request name or endpoint
	public String mainPage(HttpSession httpSession, Model model) {
		Long patientId = (long) httpSession.getAttribute("patientId");
		model.addAttribute("doctorlist", doctorSer.showAll());
		model.addAttribute("patient", patientSer.showAll());
		Optional<Patient> optionalPatient = patientSer.getPatientById1(patientId);
		model.addAttribute("pa", optionalPatient.get());
		List<Appointment> completedAppointments = appointmentSer.showAllPatientApp(patientId);
		model.addAttribute("completeAppo", completedAppointments);

		return "patientHome"; // index.html view page
	}

// =================================================================
// ===================Show Profile===========================

	@GetMapping("/showPatientProfile")
	public String showPatientProfile(Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patientId");
		if (patientId != null) {
			// Fetch the specific shop by ID
			Optional<Patient> optionalPatient = patientSer.getPatientById1(patientId);
			if (optionalPatient.isPresent()) {
				model.addAttribute("patient", optionalPatient.get());
			} else {
				model.addAttribute("orderError", "Patient not found for ID: " + patientId);
			}
		} else {
			model.addAttribute("orderError", "Patient ID not found in session.");
		}
		return "editPatientprofile";
	}

	// =================================================================
	// ===================Edit Profile===========================

	/*
	 * @PostMapping("/addAppointment/{assignedId}") // request name or endpoint
	 * public String addAppointment(@PathVariable("assignedId") long assignedId) {
	 * Long patientId = (Long) httpSession.getAttribute("patientId"); Patient
	 * patient = patientSer.getPatientById(patientId); if (patientId == null) {
	 * return "redirect:/patient/login"; }
	 * 
	 * AssignedAppointment assigned = assignedSer.getTimeSlotsById(assignedId); if
	 * (assigned == null) { return "redirect:/patient/addApp?error=InvalidTimeSlot";
	 * } int count =assignedSer.getAppointmentCount(assignedId);
	 * if(count==assigned.getLimit()) { assigned.setBooked(false);
	 * assignedSer.updateTimeSlot(assigned); return
	 * "redirect:/patient/showAllDoctor"; }
	 * 
	 * appointmentSer.bookAppointment(assigned, patient); int full =
	 * assigned.getFull()+1; assigned.setFull(full);
	 * assignedSer.updateTimeSlot(assigned); return "redirect:/patient/showApp"; }
	 */
	
	@PostMapping("/addAppointment/{assignedId}")
	public String addAppointment(@PathVariable("assignedId") long assignedId) {
	    // Get the patientId from the session
	    Long patientId = (Long) httpSession.getAttribute("patientId");
	    if (patientId == null) {
	        return "redirect:/patient/login";
	    }

	    // Fetch patient details
	    Patient patient = patientSer.getPatientById(patientId);
	    if (patient == null) {
	        return "redirect:/patient/login";
	    }

	    // Fetch the assigned appointment time slot
	    AssignedAppointment assignedTimeSlot = assignedSer.getTimeSlotsById(assignedId);
	    if (assignedTimeSlot == null) {
	        return "redirect:/patient/addApp?error=InvalidTimeSlot";
	    }

	    // Check if the time slot limit has been reached
	    int currentCount = assignedSer.getAppointmentCount(assignedId);
	    if (currentCount >= assignedTimeSlot.getLimit()) {
	        assignedTimeSlot.setBooked(false);
	        assignedSer.updateTimeSlot(assignedTimeSlot);
	        return "redirect:/patient/showAllDoctor";
	    }

	    // Book the appointment
	    appointmentSer.bookAppointment(assignedTimeSlot, patient);
	    assignedTimeSlot.setFull(assignedTimeSlot.getFull()+1);
	    assignedSer.updateTimeSlot(assignedTimeSlot);

	    return "redirect:/patient/showApp";
	}

	@GetMapping("/showAllDoctor")
	public String showDoctorPage(Model model, HttpSession httpSession) {
		Long patientId = (Long) httpSession.getAttribute("patientId");
		if (patientId == null) {
			return "redirect:/patient/login";
		}
		Patient patient = patientSer.getPatientById(patientId);
		if (patient == null) {
			return "redirect:/patient/login";
		}
		List<Doctor> doc = doctorSer.showAll();
		model.addAttribute("doctorlist", doc);
		model.addAttribute("patient", patient);
		return "showDoctorByPatient";
	}

	@GetMapping("/showDoctor")
	public String AppDoctorPage(Model model, HttpSession httpSession) {
		Long patientId = (Long) httpSession.getAttribute("patientId");
		if (patientId == null) {
			return "redirect:/patient/login";
		}
		Patient patient = patientSer.getPatientById(patientId);
		if (patient == null) {
			return "redirect:/patient/login";
		}
		List<Doctor> doc = doctorSer.showAll();
		model.addAttribute("doclist", doc);
		model.addAttribute("user", patient);
		return "showDoctorByPatient";
	}

	@GetMapping("/showApp")
	public String AppShowPage(Model model, HttpSession httpSession) {
		Long patientId = (Long) httpSession.getAttribute("patientId");
		if (patientId == null) {
			return "redirect:/patient/login";
		}

		if (patientSer.getPatientById(patientId) == null) {
			return "redirect:/patient/login";
		}
		List<Appointment> completedAppointments = appointmentSer.showAllPatientApp(patientId);
		model.addAttribute("completeAppo", completedAppointments);
		model.addAttribute("patient", patientId);
		model.addAttribute("patient", patientSer.showAll());
		Optional<Patient> optionalPatient = patientSer.getPatientById1(patientId);
		model.addAttribute("pa", optionalPatient.get());
		return "historyPage";
	}

	@GetMapping("Docdetail/{id}")
	public String docDetail(@PathVariable("id") long docid, HttpSession httpSession, Model model) {
		Long patientId = (Long) httpSession.getAttribute("patientId");
		if (patientId == null) {
			return "redirect:/patient/login";
		}
		Patient patient = patientSer.getPatientById(patientId);
		Doctor doctor = doctorSer.findById(docid);

		if (patient != null) {
			model.addAttribute("patient", patient);
		}
		if (doctor != null) {
			model.addAttribute("doctor", doctor);
		}
		return "DoctorDetailPage";
	}

	@GetMapping("/about") // request name or endpoint
	public String aboutPage(Model model) {
		Long patientId = (long) httpSession.getAttribute("patientId");

		model.addAttribute("patient", patientSer.showAll());
		Optional<Patient> optionalPatient = patientSer.getPatientById1(patientId);
		model.addAttribute("pa", optionalPatient.get());

		return "aboutPage"; // index.html view page
	}

	@GetMapping("/contactPage") // request name or endpoint
	public String contactPage(Model model) {
		Long patientId = (long) httpSession.getAttribute("patientId");
		Optional<Patient> optionalPatient = patientSer.getPatientById1(patientId);
		model.addAttribute("pa", optionalPatient.get());
		model.addAttribute("patient", patientSer.showAll());

		return "contactPage"; // index.html view page
	}

	@GetMapping("/addAppointment1")
	public String addingAppointmentPage(Model model) {
		Long patientId = (long) httpSession.getAttribute("patientId");

		model.addAttribute("patient", patientSer.showAll());
		Optional<Patient> optionalPatient = patientSer.getPatientById1(patientId);
		model.addAttribute("pa", optionalPatient.get());
		return "addAppointment"; // index.html view page
	}

}
