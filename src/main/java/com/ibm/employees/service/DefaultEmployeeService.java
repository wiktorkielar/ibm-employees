package com.ibm.employees.service;

import com.ibm.employees.exception.EmployeeNotFoundException;
import com.ibm.employees.exception.EmployeesNotFoundException;
import com.ibm.employees.model.EmployeeEntity;
import com.ibm.employees.model.EmployeeRequest;
import com.ibm.employees.model.EmployeeResponse;
import com.ibm.employees.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponse> getEmployees() {

        List<EmployeeResponse> employeeResponseList = employeeRepository.findAll()
                .stream()
                .map(employeeEntity -> EmployeeResponse.builder()
                        .uuid(employeeEntity.getUuid())
                        .firstName(employeeEntity.getFirstName())
                        .lastName(employeeEntity.getLastName())
                        .jobRole(employeeEntity.getJobRole())
                        .created(employeeEntity.getCreated())
                        .build())
                .toList();

        if (employeeResponseList.isEmpty()) {
            throw new EmployeesNotFoundException();
        }

        return employeeResponseList;
    }

    @Override
    public EmployeeResponse getEmployee(String uuid) {

        EmployeeEntity employeeEntity = employeeRepository
                .findEmployeeEntityByUuid(uuid)
                .orElseThrow(() -> new EmployeeNotFoundException(uuid));

        return EmployeeResponse.builder()
                .uuid(employeeEntity.getUuid())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .jobRole(employeeEntity.getJobRole())
                .created(employeeEntity.getCreated())
                .build();
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {

        EmployeeEntity savedEmployeeEntity = employeeRepository.save(EmployeeEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .jobRole(employeeRequest.getJobRole())
                .created(LocalDateTime.now())
                .build());

        return EmployeeResponse.builder()
                .uuid(savedEmployeeEntity.getUuid())
                .firstName(savedEmployeeEntity.getFirstName())
                .lastName(savedEmployeeEntity.getLastName())
                .jobRole(savedEmployeeEntity.getJobRole())
                .created(savedEmployeeEntity.getCreated())
                .build();
    }

    @Override
    public EmployeeResponse updateEmployee(String uuid, EmployeeRequest employeeRequest) {

        EmployeeEntity employeeEntity = employeeRepository
                .findEmployeeEntityByUuid(uuid)
                .orElseThrow(() -> new EmployeeNotFoundException(uuid));

        EmployeeEntity updatedEmployeeEntity = employeeRepository.save(EmployeeEntity.builder()
                .id(employeeEntity.getId())
                .uuid(employeeEntity.getUuid())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .jobRole(employeeRequest.getJobRole())
                .created(employeeEntity.getCreated())
                .build());

        return EmployeeResponse.builder()
                .uuid(updatedEmployeeEntity.getUuid())
                .firstName(updatedEmployeeEntity.getFirstName())
                .lastName(updatedEmployeeEntity.getLastName())
                .jobRole(updatedEmployeeEntity.getJobRole())
                .created(updatedEmployeeEntity.getCreated())
                .build();
    }
}
