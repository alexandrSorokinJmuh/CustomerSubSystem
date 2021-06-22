package com.example.offer_sub_system.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Value("${http.address}")
    String originAddress;
    @Value("${http.customer.address}")
    String customerOriginAddress;


    @GetMapping("/login")
    public String getLoginPage() {
        return "redirect:" + customerOriginAddress + "/auth/login";
    }

    @GetMapping("/logout")
    public String getLogoutPage() {
        return "redirect:" + customerOriginAddress + "/auth/logout";
    }

}
