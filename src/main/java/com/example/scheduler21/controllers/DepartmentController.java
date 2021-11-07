package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Department;
import com.example.scheduler21.services.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/new")
    public String displayAddDepartmentForm(Model model) {
        Department department = new Department();

        model.addAttribute("department", department);

        return "departments/add-department";
    }

    @PostMapping("/save")
    public String saveDepartment(Department department) {
        departmentService.saveDepartment(department);

        return "redirect:/departments";
    }

    @GetMapping("/update")
    public String displayUpdateDepartmentForm(@RequestParam("id") Integer id, Model model){
        Department department = departmentService.findById(id);

        model.addAttribute("department", department);

        return "departments/update-department";
    }

    @GetMapping("/delete")
    public String deleteDepartment(@RequestParam("id") Integer id) {
        departmentService.deleteDepartmentById(id);

        return "redirect:/departments";
    }

}
