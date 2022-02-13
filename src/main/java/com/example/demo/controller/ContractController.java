package com.example.demo.controller;

import com.example.demo.models.Contract;
import com.example.demo.models.Employee;
import com.example.demo.models.ServiceProvision;
import com.example.demo.services.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ContractController {
    public ContractService contractService;

    @ExceptionHandler({IllegalArgumentException.class})
    public void handleException() {

    }

    @GetMapping("/contract")
    public List<Contract> getContracts() {
        return contractService.getContracts();
    }

    @GetMapping("/contract/{id}")
    public Contract getContractById(@PathVariable Long id) {
        return contractService.get(id);
    }

    @PostMapping("/contract")
    public Contract saveContract(@RequestBody Contract contract) {
        return contractService.save(contract);
    }

    @DeleteMapping("/contract/{id}")
    public Contract deleteContract(@PathVariable Long id) {
        return contractService.delete(id);
    }

    @PutMapping("/contract/{id}")
    public Contract updateContract(@PathVariable Long id, @RequestBody Contract contract) {
        return contractService.update(id, contract);
    }

    @PostMapping("/contract/service-provision")
    public ResponseEntity provideService(@RequestBody ServiceProvision serviceProvision) {
        return contractService.associateEmployeeToContract(serviceProvision);
    }

    @GetMapping("/contract/service-provision")
    public List<ServiceProvision> getServiceProvisions() {
        return contractService.getServiceProvisions();
    }

    @DeleteMapping("/contract/service-provision/{id}")
    public ServiceProvision deleteServiceProvision(@PathVariable Long id) {
        return contractService.deleteServiceProvision(id);
    }
}
