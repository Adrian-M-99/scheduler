package com.example.scheduler21.security;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.entities.Role;
import com.example.scheduler21.services.PatientService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("register")
public class RegistrationController {

    private final PatientService patientService;

    public RegistrationController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());

        return "register/register";
    }

    @PostMapping("/process_register")
    public String processRegister(Patient patient, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        patientService.register(patient, getUrl(request));

        patientService.save(patient);

        return "register/register_success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (patientService.verify(code))
            return "register/verify_success";
        else
            return "register/verify_fail";
    }

    private String getUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();

        return url.replace(request.getServletPath(), "");
    }


}
