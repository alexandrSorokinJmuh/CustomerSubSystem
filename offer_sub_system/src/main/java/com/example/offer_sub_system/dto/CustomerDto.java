package com.example.offer_sub_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    String customer_id;
    String email;
    String password;
    String status;
    String role;

}
