package com.ibm.employees.controller;

import com.ibm.employees.model.EmployeeRequest;
import com.ibm.employees.model.EmployeeResponse;
import com.ibm.employees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${api.base.path}/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable String uuid) {
        return new ResponseEntity<>(employeeService.getEmployee(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String uuid, @RequestBody @Valid EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.updateEmployee(uuid, employeeRequest), HttpStatus.OK);
    }
}
