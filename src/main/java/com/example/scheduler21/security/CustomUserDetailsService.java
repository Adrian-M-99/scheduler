package com.example.scheduler21.security;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientService patientService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Patient patient = patientService.findByEmail(s);

        if (patient == null) {
            throw new UsernameNotFoundException("User not found!");
        } else
            return new CustomUserDetails(patient);
    }
}
