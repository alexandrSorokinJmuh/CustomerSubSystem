package com.example.offer_sub_system.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Value("${http.customer.address}")
    String customerOriginAddress;

    @Value("${http.offer.address}")
    String offerOriginAddress;

    @Value("${http.order.address}")
    String orderOriginAddress;

    @GetMapping("customer")
    public String customer(){
        return "redirect:" + customerOriginAddress + "/customer";
    }

    @GetMapping("order")
    public String order(){
        return "redirect:" + orderOriginAddress + "/order";
    }
}
