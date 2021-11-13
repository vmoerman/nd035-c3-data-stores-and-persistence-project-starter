package com.udacity.jdnd.course3.critter.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer {


    @GeneratedValue
    @Id
    private long id;

    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    public void addPet(Pet pet){
        pets.add(pet);
    }

}
