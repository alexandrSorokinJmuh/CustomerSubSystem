package com.example.order_sub_system.controllers;

import com.example.order_sub_system.services.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Value("${http.address}")
    String originAddress;
    @Value("${http.customer.address}")
    String customerOriginAddress;
    @Value("${jwt.header}")
    String jwtHeader;

    OrderService orderService;

    public AuthController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "redirect:" + customerOriginAddress + "/auth/login";
    }

    @GetMapping("/logout")
    public String getLogoutPage(Model model) {
        return "redirect:" + customerOriginAddress + "/auth/logout";
    }

}
