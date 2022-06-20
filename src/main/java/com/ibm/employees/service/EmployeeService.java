package com.ibm.employees.service;

import com.ibm.employees.model.EmployeeRequest;
import com.ibm.employees.model.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponse> getEmployees();

    EmployeeResponse getEmployee(String uuid);

    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse updateEmployee(String uuid, EmployeeRequest employeeRequest);
}
