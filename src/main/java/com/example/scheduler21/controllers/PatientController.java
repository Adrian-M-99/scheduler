package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.entities.Role;
import com.example.scheduler21.entities.User;
import com.example.scheduler21.services.PatientService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String displayPatients(Model model) {
        List<Patient> patients = patientService.findAll();

        model.addAttribute("patients", patients);

        return "patients/patients";
    }

    @GetMapping("/new")
    public String displayAddPatientForm(Model model) {
        Patient patient = new Patient();

        model.addAttribute("patient", patient);

        return "patients/add-patient";
    }

    @PostMapping("/save")
    public String savePatient(Patient patient) {
        patient.setRole(Role.PATIENT);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(patient.getPassword());
        patient.setPassword(encodedPassword);

        patientService.save(patient);

        return "redirect:/patients";
    }


    @GetMapping("/update")
    public String displayUpdatePatientForm(@RequestParam("id") Integer id, Model model){
        Patient patient = patientService.findById(id);

        model.addAttribute("patient", patient);

        return "patients/update-patient";
    }

    @GetMapping("/delete")
    public String deletePatient(@RequestParam("id") Integer id) {
        patientService.deleteById(id);

        return "redirect:/patients";
    }

}
