package com.example.scheduler21.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Appointment {

    @Id
    @SequenceGenerator(
            name = "appointment_sequence",
            sequenceName = "appointment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appointment_sequence"
    )
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate scheduledDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime scheduledTime;


    @ManyToOne
    @JoinColumn(
            name = "doctor_id",
            referencedColumnName = "id"
    )
    @JsonBackReference
    private Doctor doctor;


    @ManyToOne
    @JoinColumn(
            name = "patient_id",
            referencedColumnName = "id"
    )
    @JsonBackReference
    private Patient patient;

    public Appointment() {
        this.status = Status.SCHEDULED;
    }

    public Appointment(LocalDate scheduledDate, LocalTime scheduledTime, Doctor doctor, Patient patient) {
        this.status = Status.SCHEDULED;
        this.scheduledDate = scheduledDate;
        this.scheduledTime = scheduledTime;
        this.doctor = doctor;
        this.patient = patient;
    }
}
