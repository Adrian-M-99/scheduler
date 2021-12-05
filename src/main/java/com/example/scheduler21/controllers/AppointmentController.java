package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.*;

import com.example.scheduler21.services.AppointmentService;
import com.example.scheduler21.services.DoctorService;
import com.example.scheduler21.services.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



//TODO: to work on listing and updating appointments

@Controller
@RequestMapping("appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final List<LocalTime> defaultSlots = new ArrayList<>();



    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        defaultSlots.add(LocalTime.parse("09:00", formatter));
        defaultSlots.add(LocalTime.parse("09:30", formatter));
        defaultSlots.add(LocalTime.parse("10:00", formatter));
        defaultSlots.add(LocalTime.parse("10:30", formatter));
        defaultSlots.add(LocalTime.parse("11:00", formatter));
        defaultSlots.add(LocalTime.parse("11:30", formatter));
        //lunch break 12:00-13:00
        defaultSlots.add(LocalTime.parse("13:00", formatter));
        defaultSlots.add(LocalTime.parse("13:30", formatter));
        defaultSlots.add(LocalTime.parse("14:00", formatter));
        defaultSlots.add(LocalTime.parse("14:30", formatter));
        defaultSlots.add(LocalTime.parse("15:00", formatter));
        defaultSlots.add(LocalTime.parse("15:30", formatter));
        defaultSlots.add(LocalTime.parse("16:00", formatter));
        defaultSlots.add(LocalTime.parse("16:30", formatter));
    }

    @GetMapping
    public String displayAppointments(Model model) {
        List<Appointment> appointments = appointmentService.findAll();

        checkFinishedAppointments(appointments);

        model.addAttribute("appointments", appointments);

        return "appointments/appointments";
    }


    @GetMapping("/new")
    public String displayAddAppointmentForm(Model model) {
        Appointment appointment = new Appointment();
        List<Doctor> doctors = doctorService.findAll();
        List<Patient> patients = patientService.findAll();

        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctors);
        model.addAttribute("patients", patients);

        return "appointments/add-appointment";
    }


    @PostMapping("/confirm")
    public String saveAppointment(Appointment appointment, Model model, Doctor doctor) {

        model.addAttribute("availableSlots", getSlots(appointment, doctor));
        model.addAttribute("appointment", appointment);


        return "appointments/add-appointment-2";
    }


    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.saveAppointment(appointment);

        return "redirect:/appointments";
    }


    @GetMapping("/delete")
    public String deleteAppointment(@RequestParam("id") Integer id) {
        appointmentService.deleteById(id);

        return "redirect:/appointments";
    }

    @GetMapping("/update")
    public String displayUpdateAppointmentForm(@RequestParam("id") Integer id, Model model){
        Appointment appointment = appointmentService.findById(id);
        List<Doctor> doctors = doctorService.findAll();
        List<Patient> patients = patientService.findAll();

        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctors);
        model.addAttribute("patients", patients);

        return "appointments/update-appointment";
    }


//    @PostMapping("/confirm-update")
//    public String confirmAppointment(Appointment appointment, Model model, Doctor doctor) {
//
//        model.addAttribute("availableSlots", getSlots(appointment, doctor));
//        model.addAttribute("appointment", appointment);
//
//        return "appointments/update-appointment-2";
//    }

        @PostMapping("/confirm-update")
        public String confirmAppointment(@RequestParam("id") Integer id, Model model, Appointment appointment) {
        Appointment temp_appointment = appointmentService.findById(appointment.getId());

        temp_appointment.setDoctor(appointment.getDoctor());

        model.addAttribute("availableSlots", getSlots(appointment, temp_appointment.getDoctor()));
        model.addAttribute("appointment", temp_appointment);

        return "appointments/update-appointment-2";
    }


    @GetMapping("/cancel")
    public String cancelAppointment(@RequestParam("id") Integer id) {
        appointmentService.cancelById(id);

        return "redirect:/appointments";
    }




    private List<LocalTime> getSlots(Appointment appointment, Doctor doctor) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(doctor.getId(), appointment.getScheduledDate(), Status.SCHEDULED);

        List<LocalTime> slots = defaultSlots;

        appointments.forEach((temp) -> {
            slots.remove(temp.getScheduledTime());
        });

        return slots;
    }

    private void checkFinishedAppointments(List<Appointment> appointments) {
        appointments.forEach((temp) -> {
            if (temp.getScheduledDate().isBefore(LocalDate.now()) && temp.getScheduledTime().isBefore(LocalTime.now())) {
                temp.setStatus(Status.FINISHED);
            }
        });
    }

}
