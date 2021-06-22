package com.example.order_sub_system.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Value("${http.customer.address}")
    String customerOriginAddress;

    @Value("${http.offer.address}")
    String offerOriginAddress;

    @GetMapping("customer")
    public String customer(){
        return "redirect:" + customerOriginAddress + "/customer";
    }

    @GetMapping("/characteristic")
    public String characteristic() {
        return "redirect:" + offerOriginAddress + "/characteristic";
    }

    @GetMapping("/offer")
    public String offer() {
        return "redirect:" + offerOriginAddress + "/offer";
    }

    @GetMapping("/category")
    public String category() {
        return "redirect:" + offerOriginAddress + "/category";
    }
}
