package com.example.scheduler21.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Doctor extends User {

    @ManyToOne
    @JoinColumn(
            name = "department_id",
            referencedColumnName = "id"
    )
    private Department department;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    public int countAppointments() {
        return this.appointments.size();
    }

}
