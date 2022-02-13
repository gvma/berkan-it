package com.example.demo.services;

import com.example.demo.models.Company;
import com.example.demo.models.Contract;
import com.example.demo.models.Employee;
import com.example.demo.models.ServiceProvision;
import com.example.demo.repositories.ContractRepository;
import com.example.demo.repositories.ServiceProvisionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ContractService {
    private ContractRepository contractRepository;
    private ServiceProvisionRepository serviceProvisionRepository;

    public Contract get(Long id) { return contractRepository.findById(id).get(); }

    public List<Contract> getContracts() { return contractRepository.findAll(); }

    public Contract save(Contract contract) {
        return contractRepository.save(contract);
    }

    public Contract delete(Long id) {
        Contract contract = get(id);
        contractRepository.delete(contract);
        return contract;
    }

    public Contract update(Long id, Contract contract) {
        Contract oldContract = get(id);
        return contractRepository.save(updateContractAttributes(contract, oldContract));
    }

    private Contract updateContractAttributes(Contract newContract, Contract oldContract) {
        oldContract.setEndDate(newContract.getEndDate());
        oldContract.setInitialDate(newContract.getInitialDate());
        oldContract.setHiredCompany(newContract.getHiredCompany());
        oldContract.setHiringCompany(newContract.getHiringCompany());
        return oldContract;
    }

    public ResponseEntity associateEmployeeToContract(ServiceProvision serviceProvision) throws IllegalArgumentException {
        Employee employee = serviceProvision.getEmployee();
        Company hiredCompany = serviceProvision.getContract().getHiredCompany();
        if (!employee.getCompany().equals(hiredCompany)) {
            return ResponseEntity.status(HttpStatus.OK).body("O funcionário precisa pertencer a empresa contratada");
        }
        serviceProvisionRepository.save(serviceProvision);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceProvisionToJSON(serviceProvision));
    }

    public List<ServiceProvision> getServiceProvisions() {
        return serviceProvisionRepository.findAll();
    }

    public ServiceProvision deleteServiceProvision(Long id) {
        ServiceProvision serviceProvision = serviceProvisionRepository.findById(id).get();
        serviceProvisionRepository.delete(serviceProvision);
        return serviceProvision;
    }

    private String serviceProvisionToJSON(ServiceProvision serviceProvision) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode hiredCompany = mapper.createObjectNode();
        hiredCompany.put("id", serviceProvision.getContract().getHiredCompany().getId());
        hiredCompany.put("name", serviceProvision.getContract().getHiredCompany().getName());

        ObjectNode hiringCompany = mapper.createObjectNode();
        hiringCompany.put("id", serviceProvision.getContract().getHiringCompany().getId());
        hiringCompany.put("name", serviceProvision.getContract().getHiringCompany().getName());

        ObjectNode contract = mapper.createObjectNode();
        contract.put("id", serviceProvision.getContract().getId());
        contract.put("initialDate", serviceProvision.getContract().getInitialDate().toString());
        contract.put("endDate", serviceProvision.getContract().getEndDate().toString());
        contract.set("hiringCompany", hiringCompany);
        contract.set("hiredCompany", hiredCompany);

        ObjectNode employee = mapper.createObjectNode();
        employee.put("id", serviceProvision.getEmployee().getId());
        employee.put("name", serviceProvision.getEmployee().getName());
        employee.put("admissionDate", serviceProvision.getEmployee().getAdmissionDate().toString());
        employee.set("company", hiredCompany);

        ObjectNode serviceProvisionNode = mapper.createObjectNode();
        serviceProvisionNode.set("employee", employee);
        serviceProvisionNode.set("contract", contract);

        String json = "";
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(serviceProvisionNode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return json;
    }
}
