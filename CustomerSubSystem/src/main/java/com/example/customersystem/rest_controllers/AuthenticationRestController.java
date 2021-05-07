package com.example.customersystem.rest_controllers;

import com.example.customersystem.dao.CustomerDao;
import com.example.customersystem.dto.AuthenticationRequestDto;
import com.example.customersystem.entities.Customer;
import com.example.customersystem.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
