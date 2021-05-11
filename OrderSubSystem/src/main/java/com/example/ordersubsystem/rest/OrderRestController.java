package com.example.ordersubsystem.rest;

import com.example.ordersubsystem.dto.LabelAndValueDto;
import com.example.ordersubsystem.dto.OfferDto;
import com.example.ordersubsystem.dto.OrdersDto;
import com.example.ordersubsystem.entities.Orders;
import com.example.ordersubsystem.entities.Status;
import com.example.ordersubsystem.orders.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {
    OrderService orderService;

    @Value("${http.address}")
    String originAddress;
    @Value("${jwt.header}")
    String jwtHeader;

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
        offerDtoList.add(new OfferDto(1, "offer1", 5536.5f, 2
        ));
        offerDtoList.add(new OfferDto(4, "Tovar1", 23.24f, 3
        ));


        return offerDtoList;
    }

    @GetMapping("/{offer_id}/getOfferByTerm")

    public List<LabelAndValueDto> getOfferByTerm(
            @PathVariable("offer_id") int id,
            @RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<OfferDto> offerDtoList = new ArrayList<>();
        offerDtoList.add(new OfferDto(1, "offer1", 5536.5f, 2
        ));
        offerDtoList.add(new OfferDto(4, "Tovar1", 23.24f, 3
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

    @GetMapping("/{order_id}/getCategoryAndPrice")

    public ResponseEntity<?> getCategoryAndPrice(
            @PathVariable("order_id") int id
    ) {

        Orders offer = orderService.getById(id);


// request url
        String url = String.format("%s/%d/getCategoryAndPrice", originAddress, id);

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        // send POST request
        ResponseEntity<ResponseEntity> response = restTemplate.exchange(url, HttpMethod.GET, entity, ResponseEntity.class);

        // check response
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());
            ResponseEntity<?> responseBody = response.getBody();
            if (responseBody != null && responseBody.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(responseBody.getBody());
            } else {
                return ResponseEntity.badRequest().build();
            }

        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            return ResponseEntity.badRequest().build();
        }

    }


    @GetMapping("/createOrderByOfferAndToken")

    public ResponseEntity<?> createOrderByOfferAndToken(
            @RequestParam("offer_id") int offer_id,
            @RequestParam("token") String token
    ) {
        // request url
        String url = String.format("%s/createOrderByOfferAndToken", originAddress);

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // request body parameters
        Map<String, Object> map = new HashMap<>();
        map.put("offer_id", offer_id);
        map.put("token", token);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        ResponseEntity<ResponseEntity> response = restTemplate.postForEntity(url, entity, ResponseEntity.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());
            ResponseEntity<?> responseBody = response.getBody();
            if (response.getStatusCode() == HttpStatus.CREATED) {

                if (responseBody != null && responseBody.getStatusCode() == HttpStatus.OK) {
                    return ResponseEntity.ok(responseBody.getBody());
                } else {
                    return ResponseEntity.badRequest().build();
                }

            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();

    }

}
