package com.example.order_sub_system.rest;

import com.example.order_sub_system.dto.CustomerDto;
import com.example.order_sub_system.dto.LabelAndValueDto;
import com.example.order_sub_system.dto.OfferDto;
import com.example.order_sub_system.dto.OrdersDto;
import com.example.order_sub_system.entities.Orders;
import com.example.order_sub_system.entities.Status;
import com.example.order_sub_system.services.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {
    OrderService orderService;

    @Value("${http.address}")
    String originAddress;
    @Value("${http.customer.address}")
    String customerOriginAddress;
    @Value("${http.offer.address}")
    String offerOriginAddress;

    @Value("${jwt.header}")
    String jwtHeader;
    RestTemplate restTemplate = new RestTemplate();

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
    public Orders createRest(OrdersDto orders) {
        Orders order = orderService.createWithDto(orders);
        return order;
    }



    @GetMapping("allOffers")
    public List<OfferDto> getOffers(HttpServletRequest request) {

        String url = String.format("%s/offer/offers", offerOriginAddress);

        // create an instance of RestTemplate

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set(jwtHeader, orderService.getToken(request));

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        // send POST request
        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);


        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(Arrays.toString(response.getBody()));

            System.out.println(response.getBody().getClass());
            return Arrays.stream(response.getBody())
                    .map(offer -> (LinkedHashMap) offer)
                    .map(offer -> orderService.getOfferDtoByLinkedHashMap(offer))
                    .collect(Collectors.toList());

        }

        return null;
    }



    @GetMapping("allCustomers")
    private List<CustomerDto> getCustomers(HttpServletRequest request) {
        String url = String.format("%s/customer/customers", customerOriginAddress);

        // create an instance of RestTemplate

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set(jwtHeader, orderService.getToken(request));
        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        // send POST request
        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);


        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(Arrays.toString(response.getBody()));

            System.out.println(response.getBody().getClass());
            return Arrays.stream(response.getBody())
                    .map(customer -> (LinkedHashMap) customer)
                    .map(customer -> orderService.getCustomerDtoByLinkedHashMap(customer))
                    .collect(Collectors.toList());

        }

        return null;
    }

    @GetMapping("/getOfferByTerm")

    public List<LabelAndValueDto> getOfferByTerm(
            HttpServletRequest request,
            @RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<OfferDto> offerDtoList = getOffers(request);


        List<LabelAndValueDto> suggestions = offerDtoList.stream()
                .filter(offerDto -> offerDto.getName().contains(term))
                .map(offerDto -> new LabelAndValueDto(offerDto.getName(), offerDto.getOffer_id().toString()))
                .collect(Collectors.toList());
        return suggestions;
    }


    @GetMapping("/getCustomerByTerm")

    public List<LabelAndValueDto> getCustomerByTerm(
            HttpServletRequest request,
            @RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<CustomerDto> customerDtoList = getCustomers(request);


        List<LabelAndValueDto> suggestions = customerDtoList.stream()
                .filter(customerDto -> customerDto.getEmail().contains(term))
                .map(customerDto -> new LabelAndValueDto(customerDto.getEmail(), customerDto.getCustomer_id()))
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

    @DeleteMapping("/customer/{customer_id}")
    public void deleteByCustomerIdRest(@PathVariable("customer_id") int id) {
        orderService.deleteByCustomerId(id);
    }
    @DeleteMapping("/offer/{offer_id}")
    public void deleteByOfferIdRest(@PathVariable("offer_id") int id) {
        orderService.deleteByOfferId(id);
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
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());

        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            return ResponseEntity.badRequest().build();
        }

    }


    @GetMapping("/createOrderByOfferAndToken")

    public ResponseEntity<?> createOrderByOfferAndToken(
            HttpServletRequest request,
            String offer_id,
            String token
    ) {
        if (offer_id == null || offer_id.isEmpty()) {
            offer_id = request.getParameter("offer_id");
        }
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }

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
        ResponseEntity<Object> response = restTemplate.postForEntity(url, entity, Object.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());

        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            return ResponseEntity.badRequest().build();
        }

    }

}
