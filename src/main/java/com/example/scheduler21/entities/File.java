package com.example.scheduler21.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class File {

    @Id
    @SequenceGenerator(
            name = "file_sequence",
            sequenceName = "file_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_sequence"
    )    private Integer id;

    private String name;

    private String type;

    private LocalDate uploadDate;

    private LocalTime uploadTime;

    @ManyToOne
    @JoinColumn(
            name = "patient_id",
            referencedColumnName = "user_id"
    )
    @JsonBackReference
    private Patient patient;

    @ManyToOne
    @JoinColumn(
            name = "doctor_id",
            referencedColumnName = "user_id"
    )
    @JsonBackReference
    private Doctor doctor;

    @Lob
    private byte[] data;


    public File() {
        this.uploadDate = LocalDate.now();
        this.uploadTime = LocalTime.now();
    }

    public File(String name, byte[] data) {
        this.name = name;
        this.data = data;
        this.uploadDate = LocalDate.now();
        this.uploadTime = LocalTime.now();
    }

}
