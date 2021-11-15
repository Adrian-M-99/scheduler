package com.example.scheduler21.services;

import com.example.scheduler21.entities.Doctor;
import com.example.scheduler21.exceptions.DoctorNotFoundException;
import com.example.scheduler21.repositories.DoctorRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public Doctor findById(Integer id) {
        return doctorRepository.findById(id).orElseThrow(DoctorNotFoundException::new);
    }

    public void deleteById(Integer id) {
        doctorRepository.deleteById(id);
    }

    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
}
