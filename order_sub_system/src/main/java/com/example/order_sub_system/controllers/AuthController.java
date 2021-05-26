package com.example.order_sub_system.controllers;

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
    @Value("${jwt.header}")
    String jwtHeader;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "login");
        return "login";
    }

}
