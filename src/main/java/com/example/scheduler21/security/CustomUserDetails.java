package com.example.scheduler21.security;

import com.example.scheduler21.entities.Gender;
import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private Patient patient;

    public CustomUserDetails(Patient patient) {
        this.patient = patient;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = patient.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return patient.getPassword();
    }

    @Override
    public String getUsername() {
        return patient.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return patient.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return patient.isEnabled();
    }


    public String getFirstName() {
        return this.patient.getFirstName();
    }

    public String getLastName() {
        return this.patient.getLastName();
    }

    public String getPhoneNumber() {
        return this.patient.getPhoneNumber();
    }

    public LocalDate getBirthday() {
        return this.patient.getBirthday();
    }

    public Gender getGender() {
        return this.patient.getGender();
    }

    public long getAge() {
        return this.patient.getAge();
    }

    public Patient getPatient() {
        return patient;
    }
}
