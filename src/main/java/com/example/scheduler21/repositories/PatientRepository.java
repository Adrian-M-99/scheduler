package com.example.scheduler21.repositories;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient findByEmail(String email);

    Patient findByVerificationCode(String code);

    Patient findByResetPasswordToken(String token);

    @Query(value = "UPDATE patient p SET p.failed_attempts = ?1 WHERE p.email = ?2", nativeQuery = true)
    @Modifying
    void updateFailedAttempts(int failedAttempts, String email);
}



