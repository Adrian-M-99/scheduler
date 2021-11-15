package com.example.scheduler21.security.registration;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.exceptions.UserAlreadyRegisteredException;
import com.example.scheduler21.services.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {

    @Autowired
    private PatientService patientService;

    @Autowired
    PasswordEncoder passwordEncoder;


    public void register(UserData user) throws UserAlreadyRegisteredException {

        if(patientService.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyRegisteredException();
        }

        Patient patient = new Patient();

        BeanUtils.copyProperties(user, patient);

        encodePassword(patient, user);

        patientService.save(patient);
    }



    private void encodePassword(Patient patient, UserData user){
        patient.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
