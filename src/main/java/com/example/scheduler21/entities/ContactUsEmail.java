package com.example.scheduler21.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsEmail {

    @NotEmpty(message = "Field cannot be empty!")
    @Length(min = 2, message = "Please enter a valid name!")
    private String name;

    @NotEmpty(message = "Field cannot be empty!")
    @Email(message = "Please enter a valid email address!")
    private String email;

    @Pattern(regexp="[\\d]{10}", message = "Phone number has to consist of 10 digits!")
    private String phoneNumber;

    @NotEmpty(message = "Field cannot be empty!")
    @Length(min = 10, message = "Please enter a valid message!")
    private String message;

}
