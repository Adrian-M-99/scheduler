package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Doctor;
import com.example.scheduler21.services.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String displayDoctors(Model model) {
        List<Doctor> doctors = doctorService.findAll();

        model.addAttribute("doctors", doctors);

        return "doctors/doctors";
    }
}
