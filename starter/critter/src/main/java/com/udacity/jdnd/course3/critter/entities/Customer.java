package com.udacity.jdnd.course3.critter.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "customer")
    private List<Pet> pets;

}
