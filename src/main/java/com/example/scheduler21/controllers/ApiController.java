package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Appointment;
import com.example.scheduler21.exceptions.BadDateFormatException;
import com.example.scheduler21.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments/all")
    public List<Appointment> appointments() {
        return appointmentService.findAll();
    }

    @GetMapping("/appointments")
    public List<Appointment> getAppointmentsInRange(@RequestParam(value = "start", required = true) String start,
                                                    @RequestParam(value = "end", required = true) String end) {
        Date startDate = null;
        Date endDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            startDate = dateFormat.parse(start);
        } catch (ParseException e) {
            throw new BadDateFormatException("Bad date: " + start);
        }

        try {
            endDate = dateFormat.parse(end);
        } catch (ParseException e) {
            throw new BadDateFormatException("Bad date: " + end);
        }

        LocalDate startDateTime = LocalDate.ofInstant(startDate.toInstant(),
                ZoneId.systemDefault());
        LocalDate endDateTime = LocalDate.ofInstant(endDate.toInstant(),
                ZoneId.systemDefault());


        return appointmentService.findByScheduledDateBetween(startDateTime, endDateTime);
    }
}
