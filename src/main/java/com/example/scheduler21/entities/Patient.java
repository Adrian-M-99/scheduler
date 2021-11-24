package com.example.scheduler21.entities;

import lombok.*;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Patient extends User{

    private static final long PASSWORD_EXPIRATION_TIME = 3L * 30L * 24L * 60L * 60L * 1000L; // 3 months


    @Column(length = 32)
    private String verificationCode;

    @Column(length = 32)
    private String resetPasswordToken;

    private boolean enabled;

    private boolean accountNonLocked;

    private int failedAttempts;

    private Date lockTime;

    private Date passwordChangedTime;


    public Patient() {
        this.accountNonLocked = true;
        this.passwordChangedTime = new Date(System.currentTimeMillis());
    }

    public Patient(String password, String firstName, String lastName, String email, String phoneNumber, LocalDate birthday, Gender gender) {
        super(password, firstName, lastName, email, phoneNumber, birthday, gender);
        this.accountNonLocked = true;
        this.failedAttempts = 0;
        this.passwordChangedTime = new Date(System.currentTimeMillis());
    }

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public int countAppointments() {
        return this.appointments.size();
    }


    public boolean isPasswordExpired() {
        if (this.passwordChangedTime == null)
            return false;

        long currentTime = System.currentTimeMillis();
        long lastChangedTime = this.passwordChangedTime.getTime();

        return currentTime > lastChangedTime + PASSWORD_EXPIRATION_TIME;
    }

}
