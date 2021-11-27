package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.ContactUsEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("contact-us")
public class ContactUsController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailAddress;


    @GetMapping
    public String displayContactUsForm(Model model) {

        model.addAttribute("contactUsEmail", new ContactUsEmail());

        return "contact-us/contact-us";
    }

    @PostMapping("/send")
    public String sendEmail(@Valid ContactUsEmail contactUsEmail, BindingResult result) throws MessagingException, UnsupportedEncodingException {

        if (result.hasErrors()) {
            return "contact-us/contact-us";
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailAddress, "Contact Us Form");
        helper.setTo(emailAddress);

        String subject = "New message through Contact Us form";

        String content = "Hello," +
                "<p>There is a new message received through the Contact Us form:</p>" +
                "<p>Name: [[name]]</p>" +
                "<p>Email: [[email]]</p>" +
                "<p>Phone Number: [[phoneNumber]]</p>" +
                "<p>Message: [[message]]</p>";

        content = content.replace("[[name]]", contactUsEmail.getName());
        content = content.replace("[[email]]", contactUsEmail.getEmail());
        content = content.replace("[[phoneNumber]]", contactUsEmail.getPhoneNumber());
        content = content.replace("[[message]]", contactUsEmail.getMessage());

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);

        return "contact-us/contact-us-success";
    }
}
