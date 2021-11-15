package com.example.scheduler21.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

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

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthday;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(
//            name = "role_id",
//            referencedColumnName = "id"
//    )
//    private Role role = new Role();

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
