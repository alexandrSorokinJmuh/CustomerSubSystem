package com.example.customer_sub_system.controllers;

import com.example.customer_sub_system.services.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Value("${http.order.address}")
    String orderOriginAddress;
    @Value("${http.offer.address}")
    String offerOriginAddress;



    @GetMapping("/order")
    public String order() {
        return "redirect:" + orderOriginAddress + "/order";
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