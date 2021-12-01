package com.example.scheduler21.security;

import com.example.scheduler21.entities.Role;
import com.example.scheduler21.services.DoctorService;
import com.example.scheduler21.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Value("${spring.security.user.name}")
    private String adminUserName;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Value("${spring.security.user.roles}")
    private String adminRole;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(s.equals(adminUserName)) {
            return User.builder().username(adminUserName).password(adminPassword).roles(Role.ADMIN.toString()).build();
        }

        com.example.scheduler21.entities.User user = patientService.findByEmail(s);

        if (user == null)
            user = doctorService.findByEmail(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        } else
            return new CustomUserDetails(user);

    }

}
