package com.example.scheduler21.entities;

import lombok.*;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient extends User{

    public Patient(String password, String firstName, String lastName, String email, String phoneNumber, LocalDate birthday, Gender gender) {
        super(password, firstName, lastName, email, phoneNumber, birthday, gender);
    }

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public int countAppointments() {
        return this.appointments.size();
    }

}
