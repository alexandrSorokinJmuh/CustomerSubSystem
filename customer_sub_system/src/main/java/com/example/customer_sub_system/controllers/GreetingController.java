package com.example.customer_sub_system.controllers;

import com.example.customer_sub_system.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    CustomerService customerService;

    public GreetingController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }



    @GetMapping
    public String main(Map<String, Object> model) {
        model.put("some", "hello, letsCode!");
        return "main";
    }
}