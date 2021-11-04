package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Appointment;
import com.example.scheduler21.repositories.AppointmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    public String displayAppointments(Model model) {
        List<Appointment> appointments = appointmentRepository.findAll();

        model.addAttribute("appointments", appointments);

        return "appointments/appointments";
    }

}
