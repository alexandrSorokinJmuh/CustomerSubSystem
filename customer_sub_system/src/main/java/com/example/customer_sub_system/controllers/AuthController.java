package com.example.customer_sub_system.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @GetMapping("/success")
    public String getSuccessPage(HttpServletRequest request, Model model) {
        model.addAttribute("title", "login success");

        return "success";
    }
}