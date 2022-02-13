package com.example.demo.services;

import com.example.demo.models.Employee;
import com.example.demo.repositories.CompanyRepository;
import com.example.demo.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private CompanyService companyService;

    public Employee get(Long id) {
        return employeeRepository.findById(id).get();
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        System.out.println(employee);
        employee.setCompany(companyService.get(employee.getCompany().getId()));
        return employeeRepository.save(employee);
    }

    public Employee delete(Long id) {
        Employee employee = get(id);
        employeeRepository.deleteById(id);
        return employee;
    }

    @Transactional
    public Employee update(Long id, Employee employee) {
        System.out.println(employee);
        Employee oldEmployee = get(id);
        return employeeRepository.save(updateEmployeeAttributes(employee, oldEmployee));
    }

    private Employee updateEmployeeAttributes(Employee newEmployee, Employee oldEmployee) {
        oldEmployee.setName(newEmployee.getName());
        oldEmployee.setCompany(newEmployee.getCompany());
        oldEmployee.setAdmissionDate(newEmployee.getAdmissionDate());
        return oldEmployee;
    }
}
