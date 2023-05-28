package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(convertDTOToSchedule(scheduleDTO));
        return convertScheduleToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream().map(this::convertScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getAllByPet(petId).stream().map(this::convertScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getAllByEmployee(employeeId).stream().map(this::convertScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getAllByPetOwner(customerId).stream().map(this::convertScheduleToDTO).collect(Collectors.toList());
    }

    private ScheduleDTO convertScheduleToDTO(Schedule schedule){
        if(schedule == null) return null;

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Pet> pets = schedule.getPets();
        List<Employee> employees = schedule.getEmployees();

        List<Long> petIds = new ArrayList<>();
        List<Long> employeeIds = new ArrayList<>();

        if(employees != null){
            for(Employee e : employees){
                employeeIds.add(e.getId());
            }
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        if(pets != null){
            for(Pet p : pets){
                petIds.add(p.getId());
            }
            scheduleDTO.setPetIds(petIds);
        }

        return scheduleDTO;
    }

    private Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO){
        if(scheduleDTO == null) return null;

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();

        List<Pet> pets = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();

        if(employeeIds != null){
            for(Long id : employeeIds){
                try {
                    employees.add(employeeService.findById(id));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            schedule.setEmployees(employees);
        }

        if(petIds != null){
            for(Long id : petIds){
                try {
                    pets.add(petService.findById(id));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            schedule.setPets(pets);
        }

        return schedule;
    }
}
