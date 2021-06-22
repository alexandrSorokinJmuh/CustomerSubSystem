package com.example.customer_sub_system.rest_controllers;

import com.example.customer_sub_system.JwtAuthenticationException;
import com.example.customer_sub_system.dao.CustomerDao;
import com.example.customer_sub_system.dto.AuthenticationRequestDto;
import com.example.customer_sub_system.entities.Customer;
import com.example.customer_sub_system.security.JwtTokenProvider;
import com.example.customer_sub_system.services.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private CustomerDao customerDao;
    private JwtTokenProvider jwtTokenProvider;

    @Value("${http.customer.address}")
    String customerOriginAddress;

    public AuthenticationRestController(AuthenticationManager authenticationManager, CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")

    public ResponseEntity<?> authenticate(HttpServletRequest request,AuthenticationRequestDto requestDto){
        try{
            System.out.println(requestDto);
            System.out.println(request.getParameterMap().entrySet());

            System.out.println(request);
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
            Customer customer = customerDao.findByEmail(requestDto.getEmail()).orElseThrow(()->new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(requestDto.getEmail(), customer.getRole().name());
            Map<Object, Object> responseMap = new HashMap<>();
            responseMap.put("email", requestDto.getEmail());
            responseMap.put("token", token);

            return ResponseEntity.ok(responseMap);
        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("getToken")
    public String getToken(HttpServletRequest request, String email, String password) {

        if (email == null || email.isEmpty()) {
            email = request.getParameter("email");
        }
        if (password == null || password.isEmpty()) {
            password = request.getParameter("password");
        }
        RestTemplate restTemplate = new RestTemplate();
        String getTokenUrl = String.format("%s/api/auth/login", customerOriginAddress);

        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Object> tokenEntityMap = new HashMap<>();


        tokenEntityMap.put("email", email);
        tokenEntityMap.put("password", password);
        System.out.println(email);
        System.out.println(password);

        // build the request
        HttpEntity<Map<String, Object>> tokenEntity = new HttpEntity<>(tokenEntityMap, headers);

        ResponseEntity<Object> response = restTemplate.postForEntity(getTokenUrl, tokenEntity, Object.class);
        System.out.println(response.getBody());
        System.out.println(response.getStatusCode());
        if (response.getStatusCode() == HttpStatus.OK) {
            return ((HashMap) response.getBody()).get("token").toString();

        }
        return "";
    }
    @PostMapping("/getCustomerId")
    public ResponseEntity<?> getCustomerId(String token){
        if (jwtTokenProvider.validateToken(token)){

            Customer customer = customerDao.findByEmail(jwtTokenProvider.getUsername(token)).orElseThrow(() ->
                    new UsernameNotFoundException("User doesn't exists"));
            Map<Object, Object> responseMap = new HashMap<>();
            responseMap.put("customer_id", customer.getCustomer_id());

            return ResponseEntity.ok(responseMap);
        }else {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }

    }
    @GetMapping("/getByEmail")
    public Customer indexRest(HttpServletRequest request,String email) {
        if (email == null || email.isEmpty())
            email = request.getParameter("email");
        System.out.println("email: " + email);


        return customerDao.findByEmail(email).get();
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
