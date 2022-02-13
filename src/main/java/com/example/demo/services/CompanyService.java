package com.example.demo.services;

import com.example.demo.models.Company;
import com.example.demo.repositories.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public Company get(Long id) {
        return companyRepository.findById(id).get();
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company delete(Long id) {
        Company company = get(id);
        companyRepository.deleteById(id);
        return company;
    }

    public Company update(Long id, Company company) {
        Company oldCompany = companyRepository.getById(id);
        return companyRepository.save(updateCompanyAttributes(company, oldCompany));
    }

    private Company updateCompanyAttributes(Company newCompany, Company oldCompany) {
        oldCompany.setName(newCompany.getName());
        return oldCompany;
    }
}
