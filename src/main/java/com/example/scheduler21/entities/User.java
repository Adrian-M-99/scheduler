package com.example.scheduler21.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class User implements Serializable {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Integer id;

    private String password;

    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public User(String password, String firstName, String lastName, String email, String phoneNumber, LocalDate birthday, Gender gender) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
    }

    public long getAge() {
        return ChronoUnit.YEARS.between(this.birthday, LocalDate.now());
    }

}
