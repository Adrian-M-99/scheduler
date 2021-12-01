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
public interface PatientRepository extends UserRepository {

    @Query(value = "SELECT * from User u inner join Patient p on u.id = p.id WHERE u.email = ?1", nativeQuery = true)
    Patient getByEmail(String email);

    @Query(value = "SELECT * from User u inner join Patient p on u.id = p.id WHERE p.verification_code = ?1", nativeQuery = true)
    Patient getByVerificationCode(String code);

    @Query(value = "SELECT * from User u inner join Patient p on u.id = p.id WHERE p.reset_password_token = ?1", nativeQuery = true)
    Patient getByResetPasswordToken(String token);

    @Query(value = "SELECT * from users u inner join Patient p on u.id = p.user_id", nativeQuery = true)
    List<Patient> getAll();

//    @Query(value = "UPDATE Patient p SET p.failedAttempts = ?1 WHERE p. = ?2")
    @Query(value = "UPDATE Patient SET failed_attempts = ?1 WHERE id IN (SELECT u.id FROM User u WHERE u.email = ?2)", nativeQuery = true)
    @Modifying
    void updateFailedAttempts(int failedAttempts, String email);
}



