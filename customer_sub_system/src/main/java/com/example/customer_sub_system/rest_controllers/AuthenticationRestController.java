package com.example.customer_sub_system.rest_controllers;

import com.example.customer_sub_system.JwtAuthenticationException;
import com.example.customer_sub_system.dao.CustomerDao;
import com.example.customer_sub_system.dto.AuthenticationRequestDto;
import com.example.customer_sub_system.entities.Customer;
import com.example.customer_sub_system.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private CustomerDao customerDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestController(AuthenticationManager authenticationManager, CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(AuthenticationRequestDto requestDto){
        try{
            System.out.println(requestDto);
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

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
