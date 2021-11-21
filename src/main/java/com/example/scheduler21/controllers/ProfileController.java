package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.security.CustomUserDetails;
import com.example.scheduler21.services.PatientService;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("profile")
public class ProfileController {

    private PatientService patientService;

    public ProfileController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String displayProfileForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        Patient patient = patientService.findByEmail(email);

        model.addAttribute("patient", patient);

        return "profile/profile";
    }

}
