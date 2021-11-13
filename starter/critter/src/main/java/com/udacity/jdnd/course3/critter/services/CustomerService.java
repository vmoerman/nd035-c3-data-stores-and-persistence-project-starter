package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;

    public Customer save(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    public Customer getCustomer(long id){ return customerRepository.findById(id).orElse(null); }

    public Customer getOwnerByPet(long petId)
    {
        return customerRepository.findByPetsId(petId);
    }
}
