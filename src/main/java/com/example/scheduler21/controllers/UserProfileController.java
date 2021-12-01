package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.entities.User;
import com.example.scheduler21.security.CustomUserDetails;
import com.example.scheduler21.services.DoctorService;
import com.example.scheduler21.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("profile")
public class UserProfileController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public String displayProfileForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        User user = patientService.findByEmail(email);

        if (user == null)
            user = doctorService.findByEmail(email);

        model.addAttribute("user", user);

        return "profile/profile";
    }

}
