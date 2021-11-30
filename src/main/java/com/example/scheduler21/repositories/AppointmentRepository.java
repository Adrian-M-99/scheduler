package com.example.scheduler21.repositories;

import com.example.scheduler21.entities.Appointment;
import com.example.scheduler21.entities.Doctor;
import com.example.scheduler21.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("select a from Appointment a where a.status = ?3 and a.scheduledDate = ?2 and a.doctor.id = ?1")
    List<Appointment> getAppointmentsForDoctor(Integer doctorId, LocalDate scheduledDate, Status status);

    @Query("select a from Appointment  a where a.doctor.id = ?1")
    List<Appointment> getAllAppointmentsForDoctor(Integer id);

    @Transactional
    @Modifying
    @Query("update Appointment a set a.status = 'CANCELLED' where a.id = ?1")
    void cancelById(Integer id);

    List<Appointment> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);
}
