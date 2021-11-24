package com.example.scheduler21.security;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.exceptions.PatientNotFoundException;
import com.example.scheduler21.services.PatientService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class PasswordController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PasswordEncoder passwordEncoder;


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


    //change expired password
    @GetMapping("/change_password")
    public String showChangePasswordForm(Model model) {
        return "forgot_password/change_expired_password";
    }


    @PostMapping("/change_password")
    public String processChangePassword(HttpServletRequest request,
                                        Model model, RedirectAttributes ra,
                                        Authentication authentication) throws ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Patient patient = userDetails.getPatient();


        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        if (oldPassword.equals(newPassword)) {
            model.addAttribute("message", "Your new password must be different than the old one.");

            return "forgot_password/change_expired_password";
        }

        if (!passwordEncoder.matches(oldPassword, patient.getPassword())) {
            model.addAttribute("message", "Your old password is incorrect.");
            return "forgot_password/change_expired_password";

        } else {
            patientService.changePassword(patient, newPassword);
            request.logout();
            ra.addFlashAttribute("message", "You have successfully changed your password. "
                    + "Please login again.");

            return "redirect:/login";
        }

    }
}
