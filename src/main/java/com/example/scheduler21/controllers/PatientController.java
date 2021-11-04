package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.services.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
