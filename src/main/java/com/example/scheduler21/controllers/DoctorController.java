package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Department;
import com.example.scheduler21.entities.Doctor;
import com.example.scheduler21.entities.Role;
import com.example.scheduler21.services.DepartmentService;
import com.example.scheduler21.services.DoctorService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    public DoctorController(DoctorService doctorService, DepartmentService departmentService) {
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String displayDoctors(Model model) {
        List<Doctor> doctors = doctorService.findAll();

        model.addAttribute("doctors", doctors);

        return "doctors/doctors";
    }

    @GetMapping("/new")
    public String displayAddDoctorForm(Model model) {
        Doctor doctor = new Doctor();
        List<Department> departments = departmentService.findAll();


        model.addAttribute("departments", departments);
        model.addAttribute("doctor", doctor);

        return "doctors/add-doctor";
    }

    @PostMapping("/save")
    public String saveDoctor(Doctor doctor) {
        doctor.setRole(Role.STAFF);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
        doctor.setPassword(encodedPassword);

        doctorService.saveDoctor(doctor);

        return "redirect:/doctors";
    }

    @GetMapping("/update")
    public String displayUpdateDoctorForm(@RequestParam("id") Integer id, Model model){
        Doctor doctor = doctorService.findById(id);
        List<Department> departments = departmentService.findAll();

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
//        doctor.setPassword(encodedPassword);

        model.addAttribute("departments", departments);
        model.addAttribute("doctor", doctor);

        return "doctors/update-doctor";
    }

    @GetMapping("/delete")
    public String deleteDoctor(@RequestParam("id") Integer id) {
        doctorService.deleteById(id);

        return "redirect:/doctors";
    }
}
