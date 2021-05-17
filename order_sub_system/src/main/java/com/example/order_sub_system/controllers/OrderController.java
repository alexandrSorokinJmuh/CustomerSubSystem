package com.example.order_sub_system.controllers;

import com.example.order_sub_system.dto.CustomerDto;
import com.example.order_sub_system.entities.Orders;
import com.example.order_sub_system.entities.Status;
import com.example.order_sub_system.orders.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    OrderService orderService;


    @Value("${http.address}")
    String originAddress;
    @Value("${jwt.header}")
    String jwtHeader;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ModelAttribute("allOrders")
    public List<Orders> getAll() {
        List<Orders> ordersList = orderService.getAll();
        return ordersList;
    }



    @ModelAttribute("allStatus")
    public List<Status> getStatus() {
        List<Status> statusList = Arrays.asList(Status.values());
        return statusList;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("title", "Orders");
        return "order/index";
    }

    @GetMapping("/{order_id}")
    public String show(
            HttpServletRequest request,
            @PathVariable("order_id") int id,
            Model model) {
        model.addAttribute("order", orderService.getById(id));
        Orders order = orderService.getById(id);
        if (order.getCustomer_id() != null){
            model.addAttribute("customer", orderService.getCustomer(request, order.getCustomer_id().toString()));
        }
        if (order.getOffer_id() != null){
            model.addAttribute("offer", orderService.getOffer(request, order.getOffer_id().toString()));
        }
        model.addAttribute("title", "Show order");
        return "order/show";
    }

    @GetMapping("/new")
    public String newPerson(Orders order, Model model) {
        model.addAttribute("title", "Create order");
        return "order/new";
    }

    @GetMapping("/{order_id}/edit")
    public String edit(
            HttpServletRequest request,
            @PathVariable("order_id") int id,
            Model model) {
        Orders order = orderService.getById(id);
        model.addAttribute("order", order);
        if (order.getCustomer_id() != null){
            model.addAttribute("customer", orderService.getCustomer(request, order.getCustomer_id().toString()));
        }
        if (order.getOffer_id() != null){
            model.addAttribute("offer", orderService.getOffer(request, order.getOffer_id().toString()));
        }
        model.addAttribute("title", "Edit order");
        return "order/edit";
    }
}
