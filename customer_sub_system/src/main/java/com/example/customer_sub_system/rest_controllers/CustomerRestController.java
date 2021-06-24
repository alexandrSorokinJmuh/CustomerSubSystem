package com.example.customer_sub_system.rest_controllers;

import com.example.customer_sub_system.dto.CustomerDto;
import com.example.customer_sub_system.entities.Customer;
import com.example.customer_sub_system.entities.PaidType;
import com.example.customer_sub_system.services.AddressService;
import com.example.customer_sub_system.services.CustomerService;
import com.example.customer_sub_system.services.PaidTypeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRestController {

    @Value("${http.order.address}")
    String orderOriginAddress;
    @Value("${jwt.header}")
    String jwtHeader;

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

    @PostMapping("/new")
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
    public Customer deleteRest(@PathVariable("customer_id") int id, HttpServletRequest request) {

        Customer customer = customerService.getById(id);
        customerService.delete(id);
        RestTemplate restTemplate = new RestTemplate();

        String url = orderOriginAddress + "/order/customer/" + id;
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String token = request.getHeader(jwtHeader);
        if (token == null) {
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(jwtHeader))
                    .map(cookie -> cookie.getValue())
                    .findFirst()
                    .orElse(null);

        }

        headers.set(jwtHeader, token);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        // send POST request
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        return customer;
    }
}
