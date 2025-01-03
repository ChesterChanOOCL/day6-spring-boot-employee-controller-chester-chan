package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.Entity.Employee;
import com.oocl.springbootemployee.Entity.EmployeeRepository;
import com.oocl.springbootemployee.commmon.Gender;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/all")
    public List<Employee> getAll(){
        return employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id){
        return employeeRepository.getById(id);
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam Gender gender){
        return employeeRepository.getByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee save(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PutMapping
    public Employee update(@RequestBody Employee employee){
        return employeeRepository.update(employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer id){
         employeeRepository.remove(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getByPage(@RequestParam Integer page,  Integer pageSize){
        return employeeRepository.getByPage(page,pageSize);
    }

}
