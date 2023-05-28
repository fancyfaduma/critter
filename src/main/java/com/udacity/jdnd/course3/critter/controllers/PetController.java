package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return convertPetToDTO(petService.save(convertDTO_ToPet(petDTO)));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            return convertPetToDTO(petService.findById(petId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.findAll().stream().map(this::convertPetToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.findByOwner(ownerId).stream().map(this::convertPetToDTO).collect(Collectors.toList());
    }

    private PetDTO convertPetToDTO(Pet pet){
        if(pet == null) return null;

        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        Long id = pet.getOwner().getId();
        petDTO.setOwnerId(id);
        return petDTO;
    }

    private Pet convertDTO_ToPet(PetDTO petDTO){
        if(petDTO == null) return null;

        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        Customer owner = customerService.findById(petDTO.getOwnerId());
        pet.setOwner(owner);
        return pet;
    }
}
