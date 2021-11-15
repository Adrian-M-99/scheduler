package com.example.scheduler21.services;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.exceptions.PatientNotFoundException;
import com.example.scheduler21.repositories.PatientRepository;
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

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient findById(Integer id) {
        return patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);
    }

    public void deleteById(Integer id) {
        patientRepository.deleteById(id);
    }

    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public void save(Patient patient) {
        patientRepository.save(patient);
    }
}
