package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @Autowired
    CustomerService customerService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @Transactional
    public PetDTO savePet(@RequestBody PetDTO petDTO)
    {
        Pet petToSave = convertToEntity(petDTO);
        Pet savedPet = petService.savePet(petToSave);
        Customer owner = customerService.getCustomer(petDTO.getOwnerId());
        owner.addPet(petToSave);
        return convertToDTO(savedPet);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId)
    {

        return convertToDTO(petService.getPet(petId));
//        PetDTO petDTOToReturn = convertEntityToDTO(petFromDB);
//        petDTOToReturn.setOwnerId(petFromDB.getOwner().getId());
//        return petDTOToReturn;
    }

    @GetMapping
    public List<PetDTO> getPets()
    {
        List<Pet> petListFromDB = petService.getPets();
        return convertListEntitytoDTO(petListFromDB);
//        List<PetDTO> petDTOListToReturn = new ArrayList<>();
//        for (Pet pet:petListFromDB)
//        {
//            PetDTO petDTOToReturn = convertEntityToDTO(pet);
//            petDTOListToReturn.add(petDTOToReturn);
//        }
//        return petDTOListToReturn;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId)
    {
        List<Pet> petListFromDB = petService.getPetsByOwner(ownerId);
        return convertListEntitytoDTO(petListFromDB);
//        List<PetDTO> petDTOListToReturn = new ArrayList<>();
//        for (Pet pet:petListFromDB)
//        {
//            PetDTO petDTOToReturn = convertEntityToDTO(pet);
//            petDTOListToReturn.add(petDTOToReturn);
//        }
//        return petDTOListToReturn;
    }

    private Pet convertToEntity(PetDTO petDTO){
        Pet pet = modelMapper.map(petDTO,Pet.class);
        pet.setOwner(customerService.getCustomer(petDTO.getOwnerId()));
        return pet;
    }

    private PetDTO convertToDTO(Pet pet){
        PetDTO petDTO = modelMapper.map(pet,PetDTO.class);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

//    public static Pet convertDTOToEntity(PetDTO petDTO)
//    {
//        Pet pet = new Pet();
//        BeanUtils.copyProperties(petDTO, pet);
//        return pet;
//    }
//
//    public static PetDTO convertEntityToDTO(Pet pet)
//    {
//        PetDTO petDTO = new PetDTO();
//        BeanUtils.copyProperties(pet, petDTO);
//        return petDTO;
//    }

    private List<Pet> convertListDTOtoEntity(List<PetDTO> petDTOList)
    {
        List<Pet> petList = new ArrayList<>();
        for (PetDTO petDTO:petDTOList)
        {
            petList.add(convertToEntity(petDTO));
        }
        return petList;
    }

    private List<PetDTO> convertListEntitytoDTO(List<Pet> petList)
    {
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pet:petList)
        {
            petDTOList.add(convertToDTO(pet));
        }
        return petDTOList;
    }
}
