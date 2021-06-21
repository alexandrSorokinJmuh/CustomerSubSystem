package com.example.customer_sub_system.rest_controllers;

import com.example.customer_sub_system.dto.CustomerDto;
import com.example.customer_sub_system.entities.Customer;
import com.example.customer_sub_system.entities.PaidType;
import com.example.customer_sub_system.services.AddressService;
import com.example.customer_sub_system.services.CustomerService;
import com.example.customer_sub_system.services.PaidTypeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
