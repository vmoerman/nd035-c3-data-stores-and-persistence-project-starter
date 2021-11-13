package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Pet savePet(Pet pet)
    {
        Pet savedPet = petRepository.save(pet);
        return savedPet;
    }

    public Pet getPet(long id)
    {
        return petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("id"));
    }

    public List<Pet> getPets()
    {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId)
    {
        return petRepository.findByOwnerId(ownerId);
    }
}
