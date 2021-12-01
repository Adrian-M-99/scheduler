package com.example.scheduler21.security;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private PatientService patientService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails =  (CustomUserDetails) authentication.getPrincipal();

        if (userDetails.getUser() instanceof Patient) {
            if (((Patient) userDetails.getUser()).getFailedAttempts() > 0)
                patientService.resetFailedAttempts(userDetails.getUsername());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
