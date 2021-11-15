package com.example.scheduler21.security;

import com.example.scheduler21.entities.Doctor;
import com.example.scheduler21.entities.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private Patient patient;
    private Doctor doctor;

    public MyUserDetails(Patient patient) {
        this.patient = patient;
    }

    public MyUserDetails(Doctor doctor) {
        this.doctor = doctor;
    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority;
//
//        if (patient != null) {
//            authority = new SimpleGrantedAuthority(patient.getRole().toString());
//        } else {
//            authority = new SimpleGrantedAuthority(doctor.getRole().toString());
//        }
//
//        return List.of(authority);
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        if (patient != null) {
            return patient.getPassword();
        } else {
            return doctor.getPassword();
        }
    }

    @Override
    public String getUsername() {
        if (patient != null) {
            return patient.getEmail();
        } else {
            return doctor.getEmail();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
