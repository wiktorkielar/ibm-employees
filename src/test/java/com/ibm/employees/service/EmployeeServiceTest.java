package com.ibm.employees.service;

import com.ibm.employees.exception.AllEmployeesNotFoundException;
import com.ibm.employees.exception.EmployeeNotFoundException;
import com.ibm.employees.model.EmployeeEntity;
import com.ibm.employees.model.EmployeeRequest;
import com.ibm.employees.model.EmployeeResponse;
import com.ibm.employees.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ibm.employees.CommonStings.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DefaultEmployeeService employeeService;

    @Test
    @DisplayName("Should return List of EmployeeResponse for getEmployees")
    void shouldReturnListOfEmployeeResponseForGetEmployees() {

        //given
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .uuid(UUID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .created(LocalDateTime.parse(CREATED_1))
                .build();

        when(employeeRepository.findAll()).thenReturn(List.of(employeeEntity));

        //when
        List<EmployeeResponse> employeeResponses = employeeService.getAllEmployees();

        //then
        assertThat(employeeResponses).isNotNull().hasSize(1);
        assertThat(employeeResponses.get(0).getUuid()).isEqualTo(employeeEntity.getUuid());
        assertThat(employeeResponses.get(0).getFirstName()).isEqualTo(employeeEntity.getFirstName());
        assertThat(employeeResponses.get(0).getLastName()).isEqualTo(employeeEntity.getLastName());
        assertThat(employeeResponses.get(0).getJobRole()).isEqualTo(employeeEntity.getJobRole());
        assertThat(employeeResponses.get(0).getCreated()).isEqualTo(employeeEntity.getCreated());
    }

    @Test
    @DisplayName("Should throw EmployeesNotFoundException for getEmployees")
    void shouldThrowEmployeesNotFoundExceptionForGetEmployees() {

        //given
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> employeeService.getAllEmployees()).isInstanceOf(AllEmployeesNotFoundException.class);
    }


    @Test
    @DisplayName("Should return EmployeeResponse for getEmployee")
    void shouldReturnEmployeeResponseForGetEmployee() {

        //given
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .uuid(UUID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .created(LocalDateTime.parse(CREATED_1))
                .build();

        when(employeeRepository.findEmployeeEntityByUuid(UUID)).thenReturn(Optional.ofNullable(employeeEntity));

        //when
        EmployeeResponse employeeResponse = employeeService.getEmployee(UUID);

        //then
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getUuid()).isEqualTo(employeeEntity.getUuid());
        assertThat(employeeResponse.getFirstName()).isEqualTo(employeeEntity.getFirstName());
        assertThat(employeeResponse.getLastName()).isEqualTo(employeeEntity.getLastName());
        assertThat(employeeResponse.getJobRole()).isEqualTo(employeeEntity.getJobRole());
        assertThat(employeeResponse.getCreated()).isEqualTo(employeeEntity.getCreated());
    }

    @Test
    @DisplayName("Should throw EmployeeNotFoundException for getEmployee")
    void shouldThrowEmployeeNotFoundExceptionForGetEmployee() {
        //given
        when(employeeRepository.findEmployeeEntityByUuid(UUID)).thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> employeeService.getEmployee(UUID)).isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    @DisplayName("Should return EmployeeResponse for createEmployee")
    void shouldReturnEmployeeResponseForCreateEmployee() {

        //given
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .uuid(UUID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .created(LocalDateTime.parse(CREATED_1))
                .build();

        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        //when
        EmployeeResponse employeeResponse = employeeService.createEmployee(employeeRequest);

        //then
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getUuid()).isEqualTo(UUID);
        assertThat(employeeResponse.getFirstName()).isEqualTo(employeeRequest.getFirstName());
        assertThat(employeeResponse.getLastName()).isEqualTo(employeeRequest.getLastName());
        assertThat(employeeResponse.getJobRole()).isEqualTo(employeeRequest.getJobRole());
        assertThat(employeeResponse.getCreated()).isEqualTo(CREATED_1);
    }

    @Test
    @DisplayName("Should return EmployeeResponse for updateEmployee")
    void shouldReturnEmployeeResponseForUpdateEmployee() {

        //given
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .jobRole(JOB_ROLE_2)
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .uuid(UUID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .created(LocalDateTime.parse(CREATED_1))
                .build();

        EmployeeEntity employeeEntity2 = EmployeeEntity.builder()
                .id(1L)
                .uuid(UUID)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .jobRole(JOB_ROLE_2)
                .created(LocalDateTime.parse(CREATED_1))
                .build();

        when(employeeRepository.findEmployeeEntityByUuid(UUID)).thenReturn(Optional.of(employeeEntity));

        when(employeeRepository.save(employeeEntity2)).thenReturn(EmployeeEntity.builder()
                .id(1L)
                .uuid(UUID)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .jobRole(JOB_ROLE_2)
                .created(LocalDateTime.parse(CREATED_1))
                .build());

        //when
        EmployeeResponse employeeResponse = employeeService.updateEmployee(UUID, employeeRequest);

        //then
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getUuid()).isEqualTo(UUID);
        assertThat(employeeResponse.getFirstName()).isEqualTo(employeeRequest.getFirstName());
        assertThat(employeeResponse.getLastName()).isEqualTo(employeeRequest.getLastName());
        assertThat(employeeResponse.getJobRole()).isEqualTo(employeeRequest.getJobRole());
        assertThat(employeeResponse.getCreated()).isEqualTo(CREATED_1);
    }
}