package com.example.customersystem.controllers;

import com.example.customersystem.entities.Address;
import com.example.customersystem.services.AddressService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/")
    public List<Address> getAll(){
        List<Address> addressList = addressService.getAll();
        System.out.println(addressList);
        return addressList;
    }
}
