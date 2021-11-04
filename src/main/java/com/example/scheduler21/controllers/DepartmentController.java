package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Department;
import com.example.scheduler21.services.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String displayDepartments(Model model) {
        List<Department> departments = departmentService.findAll();

        model.addAttribute("departments", departments);

        return "departments/departments";
    }
}
