package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dtos.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skills)
    {
        List<Employee> employeeList = employeeRepository.getAllByDaysAvailableContains(date.getDayOfWeek());
        return employeeList.stream().filter(employee -> employee.getSkills().containsAll(skills)).collect(Collectors.toList());
    }

}
