package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Pet> findAll(){
        return petRepository.findAll();
    }

    public Pet findById(Long petId) throws Exception {
        return petRepository.findById(petId).orElseThrow(() -> new Exception("Pet wasn't found."));
    }

    public List<Pet> findByOwner(Long ownerId){
        return petRepository.findAllByOwnerId(ownerId);
    }


    public Pet save(Pet pet){
        Pet savedPet = petRepository.save(pet);
        Customer owner = savedPet.getOwner();

        List<Pet> pets = owner.getPets();

        if(pets == null){
            pets = new ArrayList<Pet>();
        }

        pets.add(savedPet);
        owner.setPets(pets);
        customerRepository.save(owner);

        return savedPet;
    }

}
