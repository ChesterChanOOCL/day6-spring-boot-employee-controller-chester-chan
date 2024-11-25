package com.oocl.springbootemployee.Entity;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Repository
public class CompanyRepository {

    private List<Company> companies = new ArrayList<>();

    // Method to get all companies
    public List<Company> getAll() {
        return companies;
    }


}