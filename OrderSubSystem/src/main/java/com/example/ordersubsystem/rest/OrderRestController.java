package com.example.ordersubsystem.rest;

import com.example.ordersubsystem.dto.LabelAndValueDto;
import com.example.ordersubsystem.dto.OfferDto;
import com.example.ordersubsystem.dto.OrdersDto;
import com.example.ordersubsystem.entities.Orders;
import com.example.ordersubsystem.entities.Status;
import com.example.ordersubsystem.orders.OrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {
    OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Orders> indexRest() {
        return orderService.getAll();
    }

    @GetMapping("/orders/{order_id}")
    public Orders showRest(@PathVariable("order_id") int id) {
        return orderService.getById(id);
    }


    @PostMapping("")
    public Orders createRest(Orders orders) {
        orders.setStatus(Status.WAIT_FOR_PAID);
        orderService.create(orders);
        return orders;
    }

    @GetMapping("allOffers")
    public List<OfferDto> getOffers() {
        List<OfferDto> offerDtoList = new ArrayList<>();
        offerDtoList.add(new OfferDto(1,"offer1",5536.5f,2
        ));
        offerDtoList.add(new OfferDto(4,"Tovar1",23.24f,3
        ));


        return offerDtoList;
    }

    @GetMapping("/{offer_id}/getOfferByTerm")

    public List<LabelAndValueDto> getOfferByTerm(
            @PathVariable("offer_id") int id,
            @RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<OfferDto> offerDtoList = new ArrayList<>();
        offerDtoList.add(new OfferDto(1,"offer1",5536.5f,2
        ));
        offerDtoList.add(new OfferDto(4,"Tovar1",23.24f,3
        ));


        List<LabelAndValueDto> suggestions = offerDtoList.stream()
                .filter(offerDto -> offerDto.getName().contains(term))
                .map(offerDto -> new LabelAndValueDto(offerDto.getName(), offerDto.getOffer_id().toString()))
                .collect(Collectors.toList());
        return suggestions;
    }

    @PutMapping("/{order_id}")
    public Orders updateRest(@PathVariable("order_id") int id,
                               OrdersDto ordersDto
    ) {

        Orders order = orderService.updateWithDto(ordersDto);

        return order;
    }

    @PatchMapping("/{order_id}")
    public Orders updateStatus(@PathVariable("order_id") int id,
                             OrdersDto ordersDto
    ) {

        Orders order = orderService.updateStatus(id, ordersDto);

        return order;
    }
    @DeleteMapping("/{order_id}")
    public Orders deleteRest(@PathVariable("order_id") int id) {
        Orders order = orderService.getById(id);
        orderService.delete(id);
        return order;
    }
}
