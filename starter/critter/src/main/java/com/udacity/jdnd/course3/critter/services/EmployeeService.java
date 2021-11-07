package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee e)
    {
        return employeeRepository.save(e);
    }

    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long id)
    {
        return employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("id"));
    }


}
