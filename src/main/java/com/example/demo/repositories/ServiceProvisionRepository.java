package com.example.demo.repositories;

import com.example.demo.models.ServiceProvision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProvisionRepository extends JpaRepository<ServiceProvision, Long> {
}
