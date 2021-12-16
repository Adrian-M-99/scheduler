package com.example.scheduler21.security;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class PasswordExpirationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        if (isUrlExcluded(httpRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Patient patient;

        if (getLoggedInCustomer() instanceof Patient) {
            patient = (Patient) getLoggedInCustomer();

            if (patient != null && patient.isPasswordExpired()) {
                displayChangePasswordPage(servletResponse, httpRequest, patient);
            }
        }

        filterChain.doFilter(httpRequest, servletResponse);

    }


    private void displayChangePasswordPage(ServletResponse servletResponse, HttpServletRequest httpRequest, Patient patient) throws IOException {
        System.out.println("Patient: " + patient.getFirstName() + " " + patient.getLastName() + " - Password Expired:");
        System.out.println("Last time password changed: " + patient.getPasswordChangedTime());
        System.out.println("Current time: " + new Date());

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String redirectURL = httpRequest.getContextPath() + "/change_password";

        httpResponse.sendRedirect(redirectURL);
    }

    private User getLoggedInCustomer() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        Object principal = null;

        if (authentication != null) {
            principal = authentication.getPrincipal();
        }

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userDetails.getUser();
        }

        return null;
    }

    //to skip static resources
    private boolean isUrlExcluded(HttpServletRequest httpRequest) {
        String url = httpRequest.getRequestURL().toString();

        return url.endsWith(".css") || url.endsWith(".png") || url.endsWith(".js")
                || url.endsWith("/change_password");
    }
}
