package com.example.demo.controller;

import com.example.demo.models.Company;
import com.example.demo.models.Employee;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.services.CompanyService;
import com.example.demo.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;

    @GetMapping("/company")
    public List<Company> getCompany() {
        return companyService.getCompanies();
    }

    @GetMapping("/company/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.get(id);
    }

    @PostMapping("/company")
    public Company saveCompany(@RequestBody Company company) {
        return companyService.save(company);
    }

    @DeleteMapping("/company/{id}")
    public Company deleteCompany(@PathVariable Long id) {
        return companyService.delete(id);
    }

    @PutMapping("/company/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyService.update(id, company);
    }
}
