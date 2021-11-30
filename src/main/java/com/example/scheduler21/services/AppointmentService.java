package com.example.scheduler21.services;

import com.example.scheduler21.entities.Appointment;
import com.example.scheduler21.entities.Doctor;
import com.example.scheduler21.entities.Status;
import com.example.scheduler21.exceptions.AppointmentNotFoundException;
import com.example.scheduler21.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsForDoctor(Integer doctorId, LocalDate scheduledDate, Status status) {
        return appointmentRepository.getAppointmentsForDoctor(doctorId, scheduledDate, status);
    }

    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public void deleteById(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public Appointment findById(Integer id) {
        return appointmentRepository.findById(id).orElseThrow(AppointmentNotFoundException::new);
    }

    public void cancelById(Integer id) {
        appointmentRepository.cancelById(id);
    }

    public List<Appointment> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findByScheduledDateBetween(startDate, endDate);
    }
}
