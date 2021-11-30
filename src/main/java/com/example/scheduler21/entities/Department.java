package com.example.scheduler21.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @SequenceGenerator(
            name = "department_sequence",
            sequenceName = "department_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "department_sequence"
    )
    private Integer id;

    @NotBlank
    private String name;


//    @OneToMany(
//            cascade = CascadeType.ALL
//    )
//    @JoinColumn(
//            name = "department_id",
//            referencedColumnName = "id"
//    )
    @OneToMany(mappedBy = "department")
    @JsonManagedReference
    private List<Doctor> doctors;


    public int countDoctors() {
        return this.doctors.size();
    }

}
