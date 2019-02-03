package com.sevadev.javaassignment.dto;

import com.sevadev.javaassignment.repo.Person;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonDTO implements Serializable {
    private String firstName;
    private String middleName;
    private String lastName;
    private AddressDTO address;

    public PersonDTO(){
        super();
    }

    public PersonDTO(Person person) {
        this();
        this.firstName = person.getFirstName();
        this.middleName = person.getMiddleName();
        this.lastName = person.getLastName();
        this.address = new AddressDTO(person.getAddress());
    }
}