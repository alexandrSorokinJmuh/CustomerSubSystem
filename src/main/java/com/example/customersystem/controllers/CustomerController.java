package com.example.customersystem.controllers;

import com.example.customersystem.dao.PaidTypeDao;
import com.example.customersystem.entities.Customer;
import com.example.customersystem.entities.PaidType;
import com.example.customersystem.services.CustomerService;
import com.example.customersystem.services.PaidTypeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    CustomerService customerService;
    PaidTypeService paidTypeService;

    public CustomerController(CustomerService customerService, PaidTypeService paidTypeService) {
        this.customerService = customerService;
        this.paidTypeService = paidTypeService;
    }

    @ModelAttribute("allCustomers")
    public List<Customer> getAll(){
        List<Customer> customerList = customerService.getAll();
        return customerList;
    }

    @ModelAttribute("allPaidType")
    public List<PaidType> getPaidTypeAll(){
        List<PaidType> paidTypeList = paidTypeService.getAll();
        return paidTypeList;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("customerList", customerService.getAll());
        return "customer/index";
    }

    @GetMapping("/{customer_id}")
    public String show(@PathVariable("customer_id") int id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("customer") Customer customer) {
        return "customer/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("customer") Customer customer) {
        customerService.create(customer);
        return "redirect:/customer";
    }

    @GetMapping("/{customer_id}/edit")
    public String edit(Model model, @PathVariable("customer_id") int id) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer/edit";
    }

    @PatchMapping("/{customer_id}")
    public String update(@ModelAttribute("customer") Customer customer, @PathVariable("customer_id") int id) {
        customerService.update(id, customer);
        return "redirect:/customer";
    }

    @DeleteMapping("/{customer_id}")
    public String delete(@PathVariable("customer_id") int id) {
        customerService.delete(id);
        return "redirect:/customer";
    }
}
