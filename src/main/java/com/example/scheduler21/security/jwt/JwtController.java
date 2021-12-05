package com.example.scheduler21.security.jwt;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jwt/api")
@AllArgsConstructor
public class JwtController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'ROLE_ADMIN')")
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }
}
