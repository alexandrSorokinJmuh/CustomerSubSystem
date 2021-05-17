package com.example.customer_sub_system.rest_controllers;

import com.example.customer_sub_system.dto.PaidTypeDto;
import com.example.customer_sub_system.entities.PaidType;
import com.example.customer_sub_system.services.PaidTypeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/paid_type", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaidTypeRestController {

    PaidTypeService paidTypeService;

    public PaidTypeRestController(PaidTypeService paidTypeService) {
        this.paidTypeService = paidTypeService;
    }

    @GetMapping("")
    public List<PaidType> getAll() {
        return paidTypeService.getAll();
    }

    @GetMapping("/{paid_type_id}")
    public PaidType showRest(@PathVariable("paid_type_id") int id) {
        return paidTypeService.getById(id);
    }


    @PostMapping("")
    public PaidType createRest(PaidType paidType) {
        paidTypeService.create(paidType);
        return paidType;
    }

    @PutMapping("/{paidType_id}")
    public PaidType updateRest(@PathVariable("paidType_id") int id,
                               PaidTypeDto paidTypeDto
    ) {

        PaidType paidType = paidTypeService.updateWithDto(paidTypeDto);

        return paidType;
    }

    @DeleteMapping("/{paidType_id}")
    public PaidType deleteRest(@PathVariable("paidType_id") int id) {
        PaidType paidType = paidTypeService.getById(id);
        paidTypeService.delete(id);
        return paidType;
    }
}
