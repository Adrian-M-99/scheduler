package com.example.scheduler21.entities;

import lombok.*;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.persistence.Column;
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

    @Column(length = 32)
    private String verificationCode;

    @Column(length = 32)
    private String resetPasswordToken;

    private boolean enabled;

    public Patient(String password, String firstName, String lastName, String email, String phoneNumber, LocalDate birthday, Gender gender, String verificationCode, String resetPasswordToken, boolean enabled) {
        super(password, firstName, lastName, email, phoneNumber, birthday, gender);
        this.verificationCode = verificationCode;
        this.resetPasswordToken = resetPasswordToken;
        this.enabled = enabled;
    }

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public int countAppointments() {
        return this.appointments.size();
    }

}
