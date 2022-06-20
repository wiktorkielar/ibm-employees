package com.ibm.employees.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private String uuid;
    private String firstName;
    private String lastName;
    private String jobRole;
    private LocalDateTime created;
}