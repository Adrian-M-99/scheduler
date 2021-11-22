package com.example.scheduler21.security;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.exceptions.PatientNotFoundException;
import com.example.scheduler21.services.PatientService;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {

    private PatientService patientService;

    public ForgotPasswordController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/forgot_password")
    public String displayForgotPasswordForm() {
        return "forgot_password/forgot_password";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(Model model, HttpServletRequest request) {
        String email = request.getParameter("email");
        String token = RandomString.make(32);

        try {
            patientService.updateResetPasswordToken(token, email);
            String resetPasswordLink = getUrl(request) + "/reset_password?token=" + token;
            patientService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email!");

        } catch (PatientNotFoundException exception) {
            model.addAttribute("error", exception.getMessage());

        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgot_password/forgot_password";
    }


    @GetMapping("/reset_password")
    public String displayResetPasswordForm(@Param(value = "token") String token, Model model) {
        Patient patient = patientService.findByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (patient == null) {
            model.addAttribute("message", "Invalid Token");
            return "forgot_password/reset_password";
        }

        return "forgot_password/reset_password";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Patient patient = patientService.findByResetPasswordToken(token);

        if (patient == null) {
            model.addAttribute("message", "Invalid Token");
        } else {
            patientService.updatePassword(patient, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "forgot_password/reset_password";
    }



    public static String getUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "");
    }
}
