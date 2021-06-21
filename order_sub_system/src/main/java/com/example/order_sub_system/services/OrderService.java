package com.example.order_sub_system.services;

import com.example.order_sub_system.dao.OrderDao;
import com.example.order_sub_system.dto.AuthenticationRequestDto;
import com.example.order_sub_system.dto.CustomerDto;
import com.example.order_sub_system.dto.OfferDto;
import com.example.order_sub_system.dto.OrdersDto;
import com.example.order_sub_system.entities.Orders;
import com.example.order_sub_system.entities.Status;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {
    OrderDao orderDao;

    @Value("${http.address}")
    String originAddress;
    @Value("${http.customer.address}")
    String customerOriginAddress;
    @Value("${http.offer.address}")
    String offerOriginAddress;
    @Value("${jwt.header}")
    String jwtHeader;
    RestTemplate restTemplate = new RestTemplate();

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Orders> getAll() {
        return orderDao.getAll();
    }

    public Orders getById(int id) {
        return orderDao.getById(id);
    }

    public Orders create(Orders order) {

        return orderDao.create(order);
    }

    public Orders update(int id, Orders order) {
        return orderDao.update(id, order);
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(jwtHeader);
        if (token == null && request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(jwtHeader))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse("");

        }
        return token;
    }

    public CustomerDto getCustomer(HttpServletRequest request, String customerId) {
        String url = String.format("%s/customer/customers/%s", customerOriginAddress, customerId);

        // create an instance of RestTemplate

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set(jwtHeader, getToken(request));
        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        System.out.println(getToken(request));
        // send POST request
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(url, HttpMethod.GET, entity, LinkedHashMap.class);


        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getBody());
            System.out.println(response.getBody().getClass());

            return getCustomerDtoByLinkedHashMap(response.getBody());

        }

        return null;
    }

    public CustomerDto getCustomerByEmail(String email) {
        String url = String.format("%s/api/auth/customers/getByEmail?email=%s", customerOriginAddress, email);
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        Map<String, Object> map = new HashMap<>();

        map.put("email", email);


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(url, HttpMethod.GET, entity, LinkedHashMap.class);


        if (response.getStatusCode() == HttpStatus.OK) {

            return getCustomerDtoByLinkedHashMap(response.getBody());

        }

        return null;
    }


    public String getAuthToken(AuthenticationRequestDto authenticationRequestDto) {
        String url = String.format("%s/api/auth/login", customerOriginAddress);

        // create an instance of RestTemplate

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        String str = "{\"email\"=\""+authenticationRequestDto.getEmail()+"\",\"password\"=\""+authenticationRequestDto.getPassword()+"\"}";

//        headers.set("requestDto", str);

        // build the request
        Map<String, Object> map = new HashMap<>();


        map.put("email", authenticationRequestDto.getEmail());
        map.put("password", authenticationRequestDto.getPassword());
        Map<String, Object> mapEntity = new HashMap<>();
        mapEntity.put("requestDto", authenticationRequestDto);


        HttpEntity<AuthenticationRequestDto> entity = new HttpEntity<>(authenticationRequestDto, headers);

        // send POST request
        System.out.println(url);
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(url, HttpMethod.POST, entity, LinkedHashMap.class);


        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getBody());
            System.out.println(response.getBody().getClass());

            return (String) response.getBody().get("token");

        }
        return null;
    }


    public void delete(int id) {
        orderDao.delete(id);
    }

    public Orders updateStatus(int id, OrdersDto ordersDto) {
        Orders orders = new Orders();
        Orders ordersOld = orderDao.getById(id);
        orders.setStatus(Status.valueOf(ordersDto.getStatus()));
        orders.setCustomer_id(ordersOld.getCustomer_id());
        orders.setPaid(ordersOld.isPaid());
        orders.setOffer_id(ordersOld.getOffer_id());
        orders.setName(ordersOld.getName());
        orders.setDeliveryTime(ordersOld.getDeliveryTime());

        return orderDao.update(ordersDto.getOrder_id(), orders);
    }

    public Orders updateWithDto(OrdersDto ordersDto) {
        Orders orders = new Orders();
        orders.setCustomer_id(ordersDto.getCustomer_id());
        orders.setStatus(Status.valueOf(ordersDto.getStatus()));
        Boolean isPaid = ordersDto.getPaid();
        if (isPaid == null)
            isPaid = false;
        orders.setPaid(isPaid);
        orders.setOffer_id(ordersDto.getOffer_id());
        orders.setName(ordersDto.getName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsed = format.parse(ordersDto.getDeliveryTime());
            orders.setDeliveryTime(parsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return orderDao.update(ordersDto.getOrder_id(), orders);
    }

    public OfferDto getOffer(HttpServletRequest request, String offer_id) {
        String url = String.format("%s/offer/offer/%s", offerOriginAddress, offer_id);

        // create an instance of RestTemplate

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set(jwtHeader, getToken(request));
        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);


        // send POST request
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(url, HttpMethod.GET, entity, LinkedHashMap.class);


        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getBody());
            System.out.println(response.getBody().getClass());

            return getOfferDtoByLinkedHashMap(response.getBody());

        }

        return null;
    }

    public OfferDto getOfferDtoByLinkedHashMap(LinkedHashMap offer) {
        OfferDto offerDto = new OfferDto();
        offerDto.setOffer_id(Integer.parseInt(offer.get("offer_id").toString()));
        offerDto.setName(offer.get("name").toString());
        offerDto.setPrice(Float.parseFloat(offer.get("price").toString()));
        LinkedHashMap category = (LinkedHashMap) offer.get("category");
        if (category != null) {
            offerDto.setCategory(Integer.parseInt(category.get("category_id").toString()));
        }
        return offerDto;
    }

    public CustomerDto getCustomerDtoByLinkedHashMap(LinkedHashMap customer) {
        return new CustomerDto(
                customer.get("customer_id").toString(),
                customer.get("email").toString(),
                customer.get("password").toString(),
                customer.get("status").toString(),
                customer.get("role").toString()
        );
    }
}
