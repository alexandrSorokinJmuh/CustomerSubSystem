package com.example.customersystem.controllers;

import com.example.customersystem.entities.PaidType;
import com.example.customersystem.services.PaidTypeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/paid_type", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaidTypeController {

    private final PaidTypeService paidTypeService;

    public PaidTypeController(PaidTypeService paidTypeService) {
        this.paidTypeService = paidTypeService;
    }

    @GetMapping("/")
    public List<PaidType> getAll(){
        List<PaidType> paidTypeList = paidTypeService.getAll();
        System.out.println(paidTypeList);
        return paidTypeList;
    }
}
