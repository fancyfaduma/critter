package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetService petService;

    public Customer findById(Long customerId){
        return customerRepository.getOne(customerId);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findByPetId(Long petId){
        try {
            return customerRepository.findByPetsId(petId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }


}
