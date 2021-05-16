package com.example.customersystem.rest_controllers;

import com.example.customersystem.dto.CustomerDto;
import com.example.customersystem.entities.Customer;
import com.example.customersystem.entities.PaidType;
import com.example.customersystem.services.AddressService;
import com.example.customersystem.services.CustomerService;
import com.example.customersystem.services.PaidTypeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRestController {
    CustomerService customerService;
    AddressService addressService;
    PaidTypeService paidTypeService;

    public CustomerRestController(CustomerService customerService, AddressService addressService, PaidTypeService paidTypeService) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.paidTypeService = paidTypeService;
    }

    @GetMapping("/customers")
    public List<Customer> indexRest() {
        return customerService.getAll();
    }

    @GetMapping("/customers/{customer_id}")
    public Customer showRest(@PathVariable("customer_id") int id) {
        return customerService.getById(id);
    }


    @GetMapping("/customers/{customer_id}/paidTypes")
    public List<PaidType> showPaidTypes(@PathVariable("customer_id") int id) {
        return customerService.getById(id).getPaidTypes();
    }

    @PostMapping("")
    public Customer createRest(Customer customer) {
        customerService.create(customer);
        return customer;
    }

    @PutMapping("/{customer_id}")
    public Customer updateRest(@PathVariable("customer_id") int id,
                               CustomerDto customerDto
    ) {

        Customer customer = customerService.updateWithDto(customerDto);

        return customer;
    }


    @DeleteMapping("/{customer_id}")
    public Customer deleteRest(@PathVariable("customer_id") int id) {
        Customer customer = customerService.getById(id);
        customerService.delete(id);
        return customer;
    }
}
