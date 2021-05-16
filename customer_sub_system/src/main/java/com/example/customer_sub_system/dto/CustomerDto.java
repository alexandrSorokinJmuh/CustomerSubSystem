package com.example.customer_sub_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

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
