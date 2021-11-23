package com.example.scheduler21.security;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private PatientService patientService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        Patient patient = patientService.findByEmail(email);

        if (patient != null) {
            if (patient.isEnabled() && patient.isAccountNonLocked()) {
                if (patient.getFailedAttempts() < PatientService.MAX_FAILED_ATTEMPTS - 1) {
                    patientService.increaseFailedAttempts(patient);
                } else {
                    patientService.lock(patient);
                    exception = new LockedException("Your account has been locked due to 5 failed attempts."
                            + " It will be unlocked after 4 hours.");
                }
            } else if (!patient.isAccountNonLocked()) {
                if (patientService.unlockWhenTimeExpired(patient)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
            }

        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
