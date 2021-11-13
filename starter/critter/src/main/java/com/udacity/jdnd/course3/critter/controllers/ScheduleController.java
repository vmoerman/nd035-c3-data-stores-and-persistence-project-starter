package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.modelmapper.ModelMapper;
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
    ModelMapper modelMapper;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule scheduleToSave = convertToEntity(scheduleDTO);
        Schedule savedSchedule = scheduleService.createSchedule(scheduleToSave);
        return convertToDTO(savedSchedule);

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        return convertListEntitytoDTO(scheduleList);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet p = petService.getPet(petId);
        List<Schedule> scheduleList = scheduleService.getScheduleForPet(p);
        return convertListEntitytoDTO(scheduleList);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee e = employeeService.getEmployee(employeeId);
        List<Schedule> scheduleList = scheduleService.getScheduleForEmployee(e);
        return convertListEntitytoDTO(scheduleList);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer c = customerService.getCustomer(customerId);
        List<Schedule> scheduleList = scheduleService.getScheduleForCustomer(c);
        return convertListEntitytoDTO(scheduleList);
    }

    // Schedule Mapper

    private Schedule convertToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = modelMapper.map(scheduleDTO,Schedule.class);
        List<Employee> employeeList = scheduleDTO.getEmployeeIds().stream()
                .map(employeeId -> employeeService.getEmployee(employeeId))
                .collect(Collectors.toList());
        List<Pet> petList = scheduleDTO.getPetIds().stream()
               .map(petId -> petService.getPet(petId))
               .collect(Collectors.toList());
        schedule.setEmployees(employeeList);
        schedule.setPets(petList);
        return schedule;
    }

    private ScheduleDTO convertToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = modelMapper.map(schedule,ScheduleDTO.class);
        List<Long> employeeIds = schedule.getEmployees().stream()
                .map(employee -> employee.getId())
                .collect(Collectors.toList());
        List<Long> petIds = schedule.getPets().stream()
                .map(pet -> pet.getId())
                .collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);
        return scheduleDTO;
    }

    private List<ScheduleDTO> convertListEntitytoDTO(List<Schedule> scheduleList)
    {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule:scheduleList)
        {
            scheduleDTOList.add(convertToDTO(schedule));
        }
        return scheduleDTOList;
    }

}
