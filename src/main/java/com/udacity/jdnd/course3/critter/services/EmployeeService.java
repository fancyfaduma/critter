package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee findById(Long employeeId) throws Exception {
        return employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> availability, Long employeeId) throws Exception {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new Exception("Employee was not found"));
        employee.setDaysAvailable(availability);
    }

    public List<Employee> findEmployeesForservice(LocalDate localDate, Set<EmployeeSkill> skills){
        List<Employee> employeesAvailable = new ArrayList<Employee>();
        List<Employee> employees  = employeeRepository.findAllByDaysAvailableContaining(localDate.getDayOfWeek());

        for(Employee e: employees){
            if(e.getSkills().containsAll(skills)){
                employeesAvailable.add(e);
            }
        }

        return employeesAvailable;

    }




}
