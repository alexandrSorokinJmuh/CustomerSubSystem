package com.example.ordersubsystem.controllers;

import com.example.ordersubsystem.dto.OfferDto;
import com.example.ordersubsystem.entities.Orders;
import com.example.ordersubsystem.entities.Status;
import com.example.ordersubsystem.orders.OrderService;
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
import java.util.stream.Collectors;

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
    public String show(@PathVariable("order_id") int id, Model model) {
        model.addAttribute("order", orderService.getById(id));
        model.addAttribute("title", "Show order");
        return "order/show";
    }

    @GetMapping("/new")
    public String newPerson(Orders order, Model model) {
        model.addAttribute("title", "Create order");
        return "order/new";
    }

    @GetMapping("/{order_id}/edit")
    public String edit(Model model, @PathVariable("order_id") int id) {
        Orders order = orderService.getById(id);
        model.addAttribute("order", order);


        model.addAttribute("title", "Edit order");
        return "order/edit";
    }
}
