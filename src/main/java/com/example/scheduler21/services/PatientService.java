package com.example.scheduler21.services;

import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.entities.Role;
import com.example.scheduler21.exceptions.PatientNotFoundException;
import com.example.scheduler21.repositories.PatientRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Integer id) {
        return patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);
    }

    public void deleteById(Integer id) {
        patientRepository.deleteById(id);
    }

    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient findByVerificationCode(String code) {
        return patientRepository.findByVerificationCode(code);
    }


    public void register(Patient patient, String url) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(patient.getPassword());
        patient.setPassword(encodedPassword);

        String randomVerificationCode = RandomString.make(32);
        patient.setVerificationCode(randomVerificationCode);
        patient.setEnabled(false);

        patient.setRole(Role.PATIENT);

        save(patient);

        sendVerificationEmail(patient, url);
    }

    private void sendVerificationEmail(Patient patient, String url) throws UnsupportedEncodingException, MessagingException {
        String toAddress = patient.getEmail();
        String fromAddress = "my.spring.project.test.email@gmail.com";
        String senderName = "Scheduler";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Scheduler.";


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", patient.getFirstName() + " " + patient.getLastName());
        String verifyURL = url + "/register/verify?code=" + patient.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);
    }


    public boolean verify(String verificationCode) {
        Patient patient = findByVerificationCode(verificationCode);

        if (patient == null || patient.isEnabled()) {
            return false;
        } else {
            patient.setVerificationCode(null);
            patient.setEnabled(true);
            save(patient);

            return true;
        }

    }

}
