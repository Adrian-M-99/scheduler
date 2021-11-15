package com.example.scheduler21.security;

import com.example.scheduler21.entities.Doctor;
import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.repositories.PatientRepository;
import com.example.scheduler21.services.DoctorService;
import com.example.scheduler21.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Patient patient = patientService.findByEmail(s);

        if (patient == null) {
            Doctor doctor = doctorService.findByEmail(s);

            if (doctor == null) {
                throw new UsernameNotFoundException("User not found!");

            } else return new MyUserDetails(doctor);


        } else return new MyUserDetails(patient);

    }
}
