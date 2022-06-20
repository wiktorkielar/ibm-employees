package com.ibm.employees.exception;

public class EmployeesNotFoundException extends RuntimeException {
    public EmployeesNotFoundException() {
        super("employees not found");
    }
}
