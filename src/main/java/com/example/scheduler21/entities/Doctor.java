package com.example.scheduler21.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name="user_id", referencedColumnName = "id")
public class Doctor extends User {

    @ManyToOne
    @JoinColumn(
            name = "department_id",
            referencedColumnName = "id"
    )
    @JsonBackReference
    private Department department;

    @OneToMany(mappedBy = "doctor")
    @JsonBackReference
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")
    @JsonBackReference
    private List<File> files;

    public int countAppointments() {
        return this.appointments.size();
    }

    public Doctor() {
        super();
        this.setRole(Role.STAFF);
    }

    public Doctor(String password, String firstName, String lastName, String email, String phoneNumber, LocalDate birthday, Gender gender) {
        super(password, firstName, lastName, email, phoneNumber, birthday, gender);
        this.setRole(Role.STAFF);
    }
}
