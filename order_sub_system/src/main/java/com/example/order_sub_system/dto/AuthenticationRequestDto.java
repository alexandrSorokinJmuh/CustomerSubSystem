package com.example.order_sub_system.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationRequestDto implements Serializable {
    private String email;
    private String password;
}
