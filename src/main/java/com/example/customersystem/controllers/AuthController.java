package com.example.customersystem.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "login");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage(Model model) {
        model.addAttribute("title", "login success");
        return "success";
    }
}