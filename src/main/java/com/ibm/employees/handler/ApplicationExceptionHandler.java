package com.ibm.employees.handler;

import com.ibm.employees.exception.AllEmployeesNotFoundException;
import com.ibm.employees.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {EmployeeNotFoundException.class, AllEmployeesNotFoundException.class})
    public Map<String, String> handleEmployeeAndEmployeesNotFoundExceptions(RuntimeException ex) {

        Map<String, String> fieldMessagePairs = new HashMap<>();
        fieldMessagePairs.put("timestamp", LocalDateTime.now().toString());
        fieldMessagePairs.put("message", ex.getMessage());

        return fieldMessagePairs;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> fieldMessagePairs = new HashMap<>();
        fieldMessagePairs.put("timestamp", LocalDateTime.now().toString());
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> fieldMessagePairs.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return fieldMessagePairs;
    }
}
