package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Pet {

    @GeneratedValue
    @Id
    private long id;
    private PetType type;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer owner;

    @ManyToMany
    private List<Schedule> schedules;

    private LocalDate birthDate;
    private String notes;


}
