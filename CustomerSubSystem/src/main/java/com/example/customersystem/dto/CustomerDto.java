package com.example.customersystem.dto;

import com.example.customersystem.entities.Customer;
import com.example.customersystem.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomerDto {
    private Integer customer_id;
    private String firstName;
    private String lastName;
    private String email;
    private String pass;
    private String phone;

    private String[] paidTypesId;
    private String addressId;


}
