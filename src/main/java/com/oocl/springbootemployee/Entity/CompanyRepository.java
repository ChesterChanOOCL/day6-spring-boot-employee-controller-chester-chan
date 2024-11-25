package com.oocl.springbootemployee.Entity;

import com.oocl.springbootemployee.commmon.Gender;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CompanyRepository {

    private List<Company> companies = new ArrayList<>();

    public List<Company> getAll() {
        return companies;
    }
    public CompanyRepository() {
        companies = new ArrayList<>();
        companies.add(new Company(1,"OOCL"));
        companies.add(new Company(2,"CargoSmart"));
        companies.add(new Company(3,"IQAX"));
    }




}