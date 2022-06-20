package com.ibm.employees.repository;

import com.ibm.employees.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findEmployeeEntityByUuid(String uuid);
}
