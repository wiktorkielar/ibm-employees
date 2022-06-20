package com.ibm.employees.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String uuid) {
        super("employee with uuid " + uuid + " not found");
    }
}
