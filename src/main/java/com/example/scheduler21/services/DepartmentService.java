package com.example.scheduler21.services;

import com.example.scheduler21.entities.Department;
import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.exceptions.DepartmentNotFoundException;
import com.example.scheduler21.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService{

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(Integer id) {
        return departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);
    }

    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    public void deleteDepartmentById(Integer id) {
        departmentRepository.deleteById(id);
    }

}
