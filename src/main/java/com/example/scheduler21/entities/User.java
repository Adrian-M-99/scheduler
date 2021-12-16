package com.example.scheduler21.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


//TODO: validators are throwing off the OAuth2; to come back to this;
//TODO: try saving the Oauth2 user using NoArgsConstructor and setters

@Entity
@Getter
@Setter
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "users")
public class User implements Serializable {

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

//    @NotBlank
    private String firstName;

//    @NotBlank
    private String lastName;

//    @Email
    private String email;

//    @NotBlank
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @NotNull
    private LocalDate birthday;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//  )
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User() {
    }

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
        if (this.birthday == null)
            return 0;
        else
        return ChronoUnit.YEARS.between(this.birthday, LocalDate.now());
    }



}
