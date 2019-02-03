package com.sevadev.javaassignment.dto;

import com.sevadev.javaassignment.repo.Address;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDTO implements Serializable {
    private String city;
    private String state;
    private String zip;
    private String country;

    public AddressDTO(){
        super();
    }

    public AddressDTO(Address address) {
        this();
        this.city = address.getCity();
        this.state = address.getState();
        this.zip = address.getZip();
        this.country = address.getCountry();
    }
}
