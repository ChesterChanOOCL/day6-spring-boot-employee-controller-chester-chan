package com.oocl.springbootemployee;

import com.oocl.springbootemployee.Entity.Company;
import com.oocl.springbootemployee.Entity.CompanyRepository;
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
import java.util.stream.Collectors;


@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureJsonTesters
public class CompanyControllerTest {
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private MockMvc client;
    @Resource
    private JacksonTester<Company> jacksonTester;
    @Resource
    private JacksonTester<List<Company>> jacksonList;

    @Test
    void should_return_all_companies_when_get_all() {
        List<Company> companies = companyRepository.getAll();
        assertEquals(3, companies.size());
    }
}
