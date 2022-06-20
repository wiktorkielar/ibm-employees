package com.ibm.employees.repository;

import com.ibm.employees.model.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.ibm.employees.CommonStings.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setup() {
        employeeEntity = EmployeeEntity.builder()
                .uuid(UUID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .created(LocalDateTime.parse(CREATED_1))
                .build();
    }

    @Test
    @DisplayName("Should return EmployeeEntity List")
    void shouldReturnEmployeeEntityList() {
        //given
        employeeRepository.save(employeeEntity);

        //when
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        //then
        assertThat(employeeEntities).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("Should return EmployeeEntity by UUID")
    void shouldReturnEmployeeEntityByUuid() {
        //given
        employeeRepository.save(employeeEntity);

        //when
        EmployeeEntity foundEmployeeEntity = employeeRepository.findEmployeeEntityByUuid(UUID).get();

        //then
        assertThat(foundEmployeeEntity).isNotNull();
    }

    @Test
    @DisplayName("Should return saved EmployeeEntity")
    void shouldReturnSavedEmployeeEntity() {

        //when
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);

        //then
        assertThat(savedEmployeeEntity).isNotNull();
        assertThat(savedEmployeeEntity.getId()).isPositive();
    }

    @Test
    @DisplayName("Should return updated EmployeeEntity")
    void shouldReturnUpdatedEmployeeEntity() {

        //given
        employeeRepository.save(employeeEntity);

        //when
        EmployeeEntity savedEmployeeEntity = employeeRepository.findEmployeeEntityByUuid(UUID).get();
        savedEmployeeEntity.setFirstName(FIRST_NAME_2);
        savedEmployeeEntity.setLastName(LAST_NAME_2);
        savedEmployeeEntity.setJobRole(JOB_ROLE_2);
        EmployeeEntity updatedEmployeeEntity = employeeRepository.save(savedEmployeeEntity);

        //then
        assertThat(updatedEmployeeEntity).isNotNull();
        assertThat(updatedEmployeeEntity.getFirstName()).isEqualTo(FIRST_NAME_2);
        assertThat(updatedEmployeeEntity.getLastName()).isEqualTo(LAST_NAME_2);
        assertThat(updatedEmployeeEntity.getJobRole()).isEqualTo(JOB_ROLE_2);
        assertThat(savedEmployeeEntity.getId()).isEqualTo(updatedEmployeeEntity.getId());
    }
}