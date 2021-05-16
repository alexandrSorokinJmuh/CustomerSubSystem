package com.example.customer_sub_system.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Value("${http.address}")
    String originAddress;
    @Value("${jwt.header}")
    String jwtHeader;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "login");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage(HttpServletRequest request, Model model) {
        model.addAttribute("title", "login success");

        // request url
        String url = originAddress + "/paid_type/";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String token = request.getHeader(originAddress);
        if (token == null){
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(jwtHeader))
                    .map(cookie -> cookie.getValue())
                    .findFirst()
                    .orElse("");
        }

        headers.set(jwtHeader, token);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        // send POST request
        ResponseEntity<Object[]> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful");

            List<Object> responseBody = Arrays.asList(response.getBody());
            System.out.println(responseBody.getClass());
            System.out.println(responseBody.size());
            for (Object obj : responseBody){
                System.out.println(obj);
                System.out.println(obj.getClass());
                LinkedHashMap k = (LinkedHashMap)obj;
            }

        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return "success";
    }
}