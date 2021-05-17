package com.example.customer_sub_system.controllers;

import com.example.customer_sub_system.dao.CustomerDao;
import com.example.customer_sub_system.entities.Address;
import com.example.customer_sub_system.entities.Customer;
import com.example.customer_sub_system.entities.PaidType;
import com.example.customer_sub_system.services.AddressService;
import com.example.customer_sub_system.services.CustomerService;
import com.example.customer_sub_system.services.PaidTypeService;
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
    AddressService addressService;

    public CustomerController(CustomerService customerService, PaidTypeService paidTypeService, AddressService addressService) {
        this.customerService = customerService;
        this.paidTypeService = paidTypeService;
        this.addressService = addressService;
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

    @ModelAttribute("allAddress")
    public List<Address> getAddressAll(){
        List<Address> addressList = addressService.getAll();
        return addressList;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("customerList", customerService.getAll());
        model.addAttribute("title", "Customers");
        return "customer/index";
    }

    @GetMapping("/{customer_id}")
    public String show(@PathVariable("customer_id") int id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        model.addAttribute("title", "Show customer");
        return "customer/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("customer") Customer customer, Model model) {
        model.addAttribute("title", "Create customer");
        return "customer/new";
    }

//    @PostMapping()
//    public String create(@ModelAttribute("customer") Customer customer) {
//        customerService.create(customer);
//        return "redirect:/customer";
//    }

    @GetMapping("/{customer_id}/edit")
    public String edit(Model model, @PathVariable("customer_id") int id) {
        model.addAttribute("customer", customerService.getById(id));
        model.addAttribute("title", "Edit customer");
        return "customer/edit";
    }

//    @PutMapping("/{customer_id}")
//    public String update(@ModelAttribute("customer") Customer customer,
//                         @RequestParam(required = false, name = "addressId") String addressId,
//                         @RequestParam(required = false, name = "paidTypeChoose") String[] paidTypes,
//                         @PathVariable("customer_id") int id
//                         ) {
//        System.out.println(Arrays.toString(paidTypes));
//        if (addressId != null && !addressId.isEmpty())
//            customer.setAddress(addressService.getById(Integer.parseInt(addressId)));
//        if (paidTypes != null)
//            customer.setPaidTypes(Arrays.stream(paidTypes)
//                    .map(paidTypeId -> paidTypeService.getById(Integer.parseInt(paidTypeId)))
//                    .collect(Collectors.toList())
//            );
//        customerService.update(customer.getCustomer_id(), customer);
//        return "redirect:/customer";
//    }
//
//    @DeleteMapping("/{customer_id}")
//    public String delete(@PathVariable("customer_id") int id) {
//        customerService.delete(id);
//        return "redirect:/customer";
//    }



}
