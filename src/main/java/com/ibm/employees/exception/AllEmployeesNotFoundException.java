package com.ibm.employees.exception;

public class AllEmployeesNotFoundException extends RuntimeException {
    public AllEmployeesNotFoundException() {
        super("employees not found");
    }
}
