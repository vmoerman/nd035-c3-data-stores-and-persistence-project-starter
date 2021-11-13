package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule)
    {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules()
    {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Pet pet)
    {
        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> getScheduleForEmployee(Employee employee) {
        List<Schedule> s = scheduleRepository.getAllByEmployeesContains(employee);
        return s;
    }

    public List<Schedule> getScheduleForCustomer(Customer customer) {
        List<List<Schedule>> superList =  customer.getPets().stream().map(pet -> scheduleRepository.getAllByPetsContains(pet)).collect(Collectors.toList());
        List<Schedule> listToReturn = new ArrayList<>();
        for (List<Schedule> list:superList) {
            listToReturn.addAll(list);
        }
        return listToReturn;
    }
}
