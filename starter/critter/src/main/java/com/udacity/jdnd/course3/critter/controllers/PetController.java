package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO)
    {
        Pet petToSave = convertDTOToEntity(petDTO);
        return convertEntityToDTO(petService.savePet(petToSave));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId)
    {
        return convertEntityToDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets()
    {
        return convertListEntitytoDTO(petService.getPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId)
    {

    }

    public static Pet convertDTOToEntity(PetDTO petDTO)
    {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    public static PetDTO convertEntityToDTO(Pet pet)
    {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

    public static List<Pet> convertListDTOtoEntity(List<PetDTO> petDTOList)
    {
        List<Pet> petList = new ArrayList<>();
        for (PetDTO petDTO:petDTOList)
        {
            petList.add(convertDTOToEntity(petDTO));
        }
        return petList;
    }

    public static List<PetDTO> convertListEntitytoDTO(List<Pet> petList)
    {
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pet:petList)
        {
            petDTOList.add(convertEntityToDTO(pet));
        }
        return petDTOList;
    }
}
