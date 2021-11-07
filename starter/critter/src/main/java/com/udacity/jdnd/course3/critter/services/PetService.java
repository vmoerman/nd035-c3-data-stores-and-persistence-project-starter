package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Pet savePet(Pet pet)
    {
        return petRepository.save(pet);
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
        return petRepository.
    }
}
