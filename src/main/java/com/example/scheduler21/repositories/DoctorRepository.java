package com.example.scheduler21.repositories;

import com.example.scheduler21.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    Doctor findByEmail(String email);
}
