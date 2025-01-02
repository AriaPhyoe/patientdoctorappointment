package com.appointment.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.appointment.model.Admin;
import com.appointment.model.Appointment;
import com.appointment.model.AssignedAppointment;
import com.appointment.model.Doctor;
import com.appointment.repository.DoctorRepository;
import com.appointment.service.AppointmentService;
import com.appointment.service.AssignedAppointmentService;
import com.appointment.service.DoctorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	DoctorService doctorSer;
	@Autowired
	DoctorRepository doctorRepo;
	@Autowired
	private AppointmentService appointmentSer;
	@Autowired
	private AssignedAppointmentService assignedAppointSer;
	@Autowired
	private HttpSession httpSession;
//	===================Admin Log In===========================

	@GetMapping("/login") // request name or endpoint
	public String startPage() {

		return "doctorlogin"; // index.html view page
	}

	@PostMapping("/loginprocessDoctor")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession httpSession) {
		String result = doctorSer.checklogin(email, password);
		if ("redirect:/doctor/main".equals(result)) {
			Doctor doctor = doctorSer.getDoctorByEmail(email);
			if (doctor != null) {

				httpSession.setAttribute("doctorEmail", doctor.getEmail());
				httpSession.setAttribute("doctorId", doctor.getDoctorId());
				return "redirect:/doctor/main"; // Redirect to index or appropriate page
			}
		} else {
			model.addAttribute("loginError", "Invalid email or password");
		}
		return "doctorLogin";
	}

//	=================================================================

//	===================Admin Sign Up===========================

	@GetMapping("/signuppage")
	public String signupPage(Model model) {
		model.addAttribute("doctor", new Doctor());
		return "doctorSignup";
	}

	@PostMapping("/signUpprocess")
	public String addProcess(@ModelAttribute("doctor") Doctor d) {
		doctorSer.saveDoctor(d);
		return "redirect:/doctor/signuppage";
	}

	@PostMapping("/registerprocessDoctor")
	public String saveAdmin(@ModelAttribute("doctor") Doctor doctor, Model model) {
		if (doctorSer.getDoctorByEmail(doctor.getEmail()) != null) {
			model.addAttribute("registerError", "This email is already registered.");
			return "doctorSignup";
		}
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
		doctorSer.save(doctor);
		return "redirect:/doctor/login";
	}

	@GetMapping("/main") // request name or endpoint
	public String mainPage(HttpSession httpSession, Model model) {
		Long doctorId = (Long) httpSession.getAttribute("doctorId");

		if (doctorId == null) {
			return "redirect:/doctor/login";
		}
		Doctor doc = doctorSer.findById(doctorId);
		if (doc != null) {
			model.addAttribute("doctor", doc);

		}

		return "mainPageDoctor";
	}

// =================================================================
	// ===================Show Profile===========================

	@GetMapping("/showDoctorProfile")
	public String showDoctorProfile(Model model, HttpSession session) {
		Long doctorId = (Long) session.getAttribute("doctorId");
		if (doctorId != null) {
			// Fetch the specific shop by ID
			Optional<Doctor> optionalDoctor = doctorSer.getDoctorById1(doctorId);
			if (optionalDoctor.isPresent()) {
				model.addAttribute("doctor", optionalDoctor.get());
			} else {
				model.addAttribute("orderError", "Doctor not found for ID: " + doctorId);
			}
		} else {
			model.addAttribute("orderError", "Doctor ID not found in session.");
		}
		return "editDoctorprofile";
	}

	// =================================================================
	// ===================Appointment===========================

	@GetMapping("/addSchduleTime")
	public String showAddTimeSlotForm(Model model) {
		model.addAttribute("Appointment", new AssignedAppointment());
		return "addSchduleTime";
	}

	/*
	 * @PostMapping("assignedTime") public String assignedTime(@RequestParam String
	 * appointmentDate,
	 * 
	 * @RequestParam String startingTime,
	 * 
	 * @RequestParam String endingTime,
	 * 
	 * @RequestParam int limit) { Long docId = (Long)
	 * httpSession.getAttribute("doctorId"); LocalDate selectedDate =
	 * LocalDate.parse(appointmentDate); LocalTime start =
	 * LocalTime.parse(startingTime); LocalTime end = LocalTime.parse(endingTime);
	 * 
	 * assignedAppointSer.saveTimeSlot(selectedDate, start, end, docId, true);
	 * 
	 * return "redirect:/doctor/main"; }
	 */
	
	/*
	 * @PostMapping("assignedTime") public String assignedTime(@RequestParam String
	 * appointmentDate,
	 * 
	 * @RequestParam String startingTime,
	 * 
	 * @RequestParam String endingTime) { // Get doctorId from the session Long
	 * docId = (Long) httpSession.getAttribute("doctorId");
	 * 
	 * // Parse the appointment date and times LocalDate selectedDate =
	 * LocalDate.parse(appointmentDate); LocalTime start =
	 * LocalTime.parse(startingTime); LocalTime end = LocalTime.parse(endingTime);
	 * 
	 * // Set the fixed limit to 3 int limit = 3;
	 * 
	 * // Save the time slot with the fixed limit
	 * assignedAppointSer.saveTimeSlot(selectedDate, start, end, docId, true,
	 * limit); return "redirect:/doctor/main"; }
	 */
	
	@PostMapping("/assignedTime")
	public String assignedTime(@RequestParam String appointmentDate, 
	                            @RequestParam String startingTime,
	                            @RequestParam String endingTime) {
	    // Get doctorId from the session
	    Long docId = (Long) httpSession.getAttribute("doctorId");

	    // Parse the appointment date and times
	    LocalDate selectedDate = LocalDate.parse(appointmentDate);
	    LocalTime start = LocalTime.parse(startingTime);
	    LocalTime end = LocalTime.parse(endingTime);

	    // Set the fixed limit to 3
	    int limit = 3;

	    // Save the time slot with the fixed limit
	    assignedAppointSer.saveTimeSlot(selectedDate, start, end, docId, true, limit);

	    return "redirect:/doctor/main";
	}
	@GetMapping("/showAppointment")
	public String showAppointment(Model model, HttpSession httpSession) {
		Long doctorId = (Long) httpSession.getAttribute("doctorId");
		if (doctorId == null) {
			return "redirect:/doctor/login";
		}

		if (doctorSer.findById(doctorId) == null) {
			return "redirect:/doctor/login";
		}

		List<Appointment> completedAppointments = appointmentSer.showByDoctorId(doctorId).stream()
				.filter(app -> "Pending".equals(app.getStatus())).collect(Collectors.toList());
		model.addAttribute("appointment", completedAppointments);
		model.addAttribute("doctor", doctorId);
		return "showAppointmentByDoctor";
	}

	@PostMapping("/confirmAppointment/{appointmentId}")
	public String confirmAppointment(@PathVariable("appointmentId") long appointmentId) {
		Appointment appointment = appointmentSer.getAppointmentById(appointmentId);

		if (appointment != null) {
			appointment.setStatus("Done");
			appointmentSer.saveAppointment(appointment);
		}

		return "redirect:/doctor/showAppointment";
	}

	@GetMapping("/showfinishedAppointment")
	public String showfinishedAppointment(Model model, HttpSession httpSession) {
		Long doctorId = (Long) httpSession.getAttribute("doctorId");
		if (doctorId == null) {
			return "redirect:/doctor/login";
		}
		Doctor doc = doctorSer.findById(doctorId);
		if (doc == null) {
			return "redirect:/doctor/login";
		}
		List<Appointment> applist = appointmentSer.showByDoctorId(doctorId).stream()
				.filter(app -> "Done".equals(app.getStatus())).collect(Collectors.toList());
		model.addAttribute("appointment", applist);
		model.addAttribute("doctor", doc);
		return "showfinishedAppointmentbydoctor";
	}

}
