package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dtos.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)
    {
        Customer customerToSave = convertToEntity(customerDTO);
        Customer savedCustomer = customerService.save(customerToSave);
        return convertToDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers()
    {
        List<Customer> allCustomers =  customerService.getAllCustomers();
        return convertListEntitytoDTO(allCustomers);

    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId)
    {
        Customer costumerFromDB = customerService.getOwnerByPet(petId);
        return convertToDTO(costumerFromDB);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO)
    {
        Employee employeeToAdd = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeService.saveEmployee(employeeToAdd);
        return convertToDTO(savedEmployee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId)
    {
        return convertToDTO(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId)
    {
        employeeService.getEmployee(employeeId).setDaysAvailable(daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeService.findEmployeesForService(employeeRequestDTO.getDate(),employeeRequestDTO.getSkills());
        return convertListEmployeeEntitytoDTO(employees);
    }

    // Customer Mapper

    private Customer convertToEntity(CustomerDTO customerDTO){
        Customer customer = modelMapper.map(customerDTO,Customer.class);
        if(customerDTO.getPetIds() != null){
            List<Pet> petList = new ArrayList<>();
            for (Long petID:customerDTO.getPetIds()) {
                petList.add(petService.getPet(petID));
            }
            customer.setPets(petList);
        }
        return customer;
    }

    private CustomerDTO convertToDTO(Customer customer){
        CustomerDTO customerDTO = modelMapper.map(customer,CustomerDTO.class);
        if(customer.getPets() != null){
            List<Long> petIds = new ArrayList<>();
            for (Pet pet:customer.getPets()) {
                petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private List<Customer> convertListDTOtoEntity(List<CustomerDTO> customerDTOList)
    {
        List<Customer> customerList = new ArrayList<>();
        for (CustomerDTO customerDTO:customerDTOList)
        {
            customerList.add(convertToEntity(customerDTO));
        }
        return customerList;
    }

    private List<CustomerDTO> convertListEntitytoDTO(List<Customer> customerList)
    {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer:customerList)
        {
            customerDTOList.add(convertToDTO(customer));
        }
        return customerDTOList;
    }

    // Employee Mapper

    private Employee convertToEntity(EmployeeDTO employeeDTO){
        Employee employee = modelMapper.map(employeeDTO,Employee.class);
        return employee;
    }

    private EmployeeDTO convertToDTO(Employee employee){
        EmployeeDTO employeeDTO = modelMapper.map(employee,EmployeeDTO.class);
        return employeeDTO;
    }

    private List<Employee> convertListEmployeeDTOtoEntity(List<EmployeeDTO> employeeDTOList)
    {
        List<Employee> employeeList = new ArrayList<>();
        for (EmployeeDTO employeeDTO:employeeDTOList)
        {
            employeeList.add(convertToEntity(employeeDTO));
        }
        return employeeList;
    }

    private List<EmployeeDTO> convertListEmployeeEntitytoDTO(List<Employee> employeeList)
    {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee:employeeList)
        {
            employeeDTOList.add(convertToDTO(employee));
        }
        return employeeDTOList;
    }
}
