package com.example.scheduler21.services;

import com.example.scheduler21.entities.Appointment;
import com.example.scheduler21.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> appointments() {
        return appointmentRepository.findAll();
    }

}
