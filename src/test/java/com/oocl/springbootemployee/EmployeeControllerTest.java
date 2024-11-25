package com.oocl.springbootemployee;

import com.oocl.springbootemployee.Entity.Employee;
import com.oocl.springbootemployee.Entity.EmployeeRepository;
import com.oocl.springbootemployee.commmon.Gender;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.oocl.springbootemployee.commmon.Gender.Male;
import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;


@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureJsonTesters
class EmployeeControllerTest {

    @Resource
    private EmployeeRepository employeeRepository;
    @Resource
    private MockMvc client;
    @Resource
    private JacksonTester<Employee> jacksonTester;
    @Resource
    private JacksonTester<List<Employee>> jacksonList;

    @Test
    public void should_return_all_employees_when_get_all_employee_given_exist() throws Exception {
        //Given
        List<Employee> employees = employeeRepository.getAll();
        //When
        String response = client.perform(MockMvcRequestBuilders.get("/employee/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andReturn().getResponse().getContentAsString();
        //Then
        List<Employee> result = jacksonList.parseObject(response);
        assertThat(result).usingRecursiveComparison().isEqualTo(employees);
    }

    @Test
    public void should_return_an_Employee_when_get_by_id_given_an_existing_id() throws Exception {
        //Given
        Employee expectEmployee = employeeRepository.getAll().get(0);
        //When
        String response = client.perform(MockMvcRequestBuilders.get("/employee/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        //Then
        Employee result = jacksonTester.parseObject(response);
        assertEquals(expectEmployee.getId(),result.getId());
    }

    @Test
    public void should_return_employee_when_get_by_gender_given_gender() throws Exception {
        //Given
        List<Employee> employees = employeeRepository.getAll();
        //When
        String response = client.perform(MockMvcRequestBuilders.get("/employee").param("gender","Male"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
                .andReturn().getResponse().getContentAsString();
        //Then
        List<Employee> resultList = jacksonList.parseObject(response);
        assertEquals(employees.get(0).getId(),resultList.get(0).getId());
        assertEquals(employees.get(2).getId(),resultList.get(1).getId());
    }

    @Test
    public void should_return_employee_when_create_employee_given_employee() throws Exception {
        //Given
        String employee = "{\n" +
                "    \"name\": \"test\",\n" +
                "    \"age\": 28,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 3000\n" +
                "}";
        //When
        client.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(28))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(new BigDecimal(3000)));
        //Then
    }

    @Test
    void should_return_employee_with_testPut_as_name_and_salary_13000_when_update_employee_with_id_1_given_employee() throws Exception {
        // Given
        List<Employee> employees = employeeRepository.getAll();
        Employee oldEmployee = employees.get(0);

        String employeeJsonString = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"testPut\",\n" +
                "    \"age\": 21,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 13000\n" +
                "}";
        //When
        client.perform(MockMvcRequestBuilders.put("/employee").contentType(MediaType.APPLICATION_JSON).content(employeeJsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testPut"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(new BigDecimal(13000)));
    }


    // Delete

    @Test
    void should_return_null_and_list_cannot_see_that_employee_when_delete_an_employee() throws Exception {
        // Given
        List<Employee> employees = employeeRepository.getAll();
        Employee employee = employees.get(0);

        // When
        client.perform(MockMvcRequestBuilders.delete("/employee/" + employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Update the employee list after deletion
        employees = employeeRepository.getAll();

        // Then
        assertFalse(employees.contains(employee));
    }

}