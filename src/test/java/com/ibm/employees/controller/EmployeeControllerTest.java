package com.ibm.employees.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.employees.model.EmployeeRequest;
import com.ibm.employees.model.EmployeeResponse;
import com.ibm.employees.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static com.ibm.employees.CommonStings.*;
import static com.ibm.employees.model.EmployeeRequest.MESSAGE_AT_LEAST_2_CHARACTERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerTest {


    @Value("${api.base.path}")
    private String apiBasePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setup() {
        employeeResponse = EmployeeResponse.builder()
                .uuid(UUID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .created(LocalDateTime.parse(CREATED_1))
                .build();
    }


    @Test
    @DisplayName("Should serialize objects and return Http Status 200 for getEmployees.")
    void shouldSerializeObjectsAndReturnHttpStatus200ForGetEmployees() throws Exception {

        //given
        String expectedResponse = "[\n" +
                "    {\n" +
                "        \"uuid\": \"7fe7acdb-0bf4-428a-81ec-689fd2942084\",\n" +
                "        \"firstName\": \"John\",\n" +
                "        \"lastName\": \"Doe\",\n" +
                "        \"jobRole\": \"Java Developer\",\n" +
                "        \"created\": \"2022-06-18T18:58:58.2070328\"\n" +
                "    }\n" +
                "]";

        when(employeeService.getEmployees()).thenReturn(List.of(employeeResponse));

        //when
        MvcResult mvcResult = mockMvc.perform(get(apiBasePath + "/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
    }

    @Test
    @DisplayName("Should serialize object and return Http Status 200 for getEmployee")
    void shouldSerializeObjectAndReturnHttpStatus200ForGetEmployee() throws Exception {

        //given
        String expectedResponse = "{\n" +
                "    \"uuid\": \"7fe7acdb-0bf4-428a-81ec-689fd2942084\",\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Doe\",\n" +
                "    \"jobRole\": \"Java Developer\",\n" +
                "    \"created\": \"2022-06-18T18:58:58.2070328\"\n" +
                "}";

        when(employeeService.getEmployee(UUID)).thenReturn(employeeResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(get(apiBasePath + "/employees/{uuid}", UUID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
    }

    @Test
    @DisplayName("Should serialize and validate correct object and return Http Status 201 for createEmployee")
    void shouldSerializeAndValidateCorrectObjectAndReturnHttpStatus201tForCreateEmployee() throws Exception {

        //given
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .build();

        String expectedResponse = "{\n" +
                "    \"uuid\": \"7fe7acdb-0bf4-428a-81ec-689fd2942084\",\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Doe\",\n" +
                "    \"jobRole\": \"Java Developer\",\n" +
                "    \"created\": \"2022-06-18T18:58:58.2070328\"\n" +
                "}";

        when(employeeService.createEmployee(employeeRequest)).thenReturn(employeeResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(post(apiBasePath + "/employees/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
    }

    @Test
    @DisplayName("Should validate incorrect object and return Http Status 400 for createEmployee")
    void shouldValidateIncorrectObjectAndReturnHttpStatus400ForCreateEmployee() throws Exception {

        //given
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName(FIRST_NAME_1_FIRST_CHAR)
                .lastName(LAST_NAME_1_FIRST_CHAR)
                .jobRole(JOB_ROLE_1_FIRST_CHAR)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post(apiBasePath + "/employees/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(employeeRequest)));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(MESSAGE_AT_LEAST_2_CHARACTERS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(MESSAGE_AT_LEAST_2_CHARACTERS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobRole", CoreMatchers.is(MESSAGE_AT_LEAST_2_CHARACTERS)));
    }

    @Test
    @DisplayName("Should validate null object and return Http Status 400 for createEmployee")
    void shouldValidateNullObjectAndReturnHttpStatus400ForCreateEmployee() throws Exception {

        //given
        EmployeeRequest employeeRequest = new EmployeeRequest();

        //when
        //then
        mockMvc.perform(post(apiBasePath + "/employees/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should serialize and validate correct object and return Http Status 200 for updateEmployee")
    void shouldSerializeAndValidateCorrectObjectAndReturnHttpStatus200tForUpdateEmployee() throws Exception {

        //given
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .jobRole(JOB_ROLE_1)
                .build();

        String expectedResponse = "{\n" +
                "    \"uuid\": \"7fe7acdb-0bf4-428a-81ec-689fd2942084\",\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Doe\",\n" +
                "    \"jobRole\": \"Java Developer\",\n" +
                "    \"created\": \"2022-06-18T18:58:58.2070328\"\n" +
                "}";

        when(employeeService.updateEmployee(UUID, employeeRequest)).thenReturn(EmployeeResponse.builder()
                .uuid(UUID)
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .jobRole(employeeRequest.getJobRole())
                .created(LocalDateTime.parse(CREATED_1))
                .build());

        //when
        MvcResult mvcResult = mockMvc.perform(put(apiBasePath + "/employees/{uuid}", UUID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
    }

    @Test
    @DisplayName("Should validate incorrect object and return Http Status 400 for updateEmployee")
    void shouldValidateIncorrectObjectAndReturnHttpStatus400ForUpdateEmployee() throws Exception {

        //given
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .firstName(FIRST_NAME_1_FIRST_CHAR)
                .lastName(LAST_NAME_1_FIRST_CHAR)
                .jobRole(JOB_ROLE_1_FIRST_CHAR)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(put(apiBasePath + "/employees/{uuid}", UUID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(employeeRequest)));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(MESSAGE_AT_LEAST_2_CHARACTERS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(MESSAGE_AT_LEAST_2_CHARACTERS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobRole", CoreMatchers.is(MESSAGE_AT_LEAST_2_CHARACTERS)));
    }

    @Test
    @DisplayName("Should validate null object and return Http Status 400 for updateEmployee")
    void shouldValidateNullObjectAndReturnHttpStatus400tForUpdateEmployee() throws Exception {

        //given
        EmployeeRequest employeeRequest = new EmployeeRequest();

        //when
        //then
        mockMvc.perform(put(apiBasePath + "/employees/{uuid}", UUID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isBadRequest());
    }
}