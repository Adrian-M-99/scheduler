package com.example.scheduler21.services;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}
