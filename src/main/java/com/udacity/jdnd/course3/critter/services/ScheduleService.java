package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getAllByPet(Long petId){
        return scheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> getAllByEmployee(Long employeeId){
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    public List<Schedule> getAllByPetOwner(Long customerId){
        return scheduleRepository.findAllByPetsOwnerId(customerId);
    }
}
