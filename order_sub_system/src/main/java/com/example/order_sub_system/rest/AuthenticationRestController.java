package com.example.order_sub_system.rest;


import com.example.order_sub_system.dto.AuthenticationRequestDto;
import com.example.order_sub_system.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private OrderService orderService;

    public AuthenticationRestController(AuthenticationManager authenticationManager, OrderService orderService) {
        this.authenticationManager = authenticationManager;
        this.orderService = orderService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(AuthenticationRequestDto requestDto){
        try{
            System.out.println(requestDto);
            String email = requestDto.getEmail();

            String token = orderService.getAuthToken(requestDto);

            Map<Object, Object> responseMap = new HashMap<>();
            responseMap.put("email", requestDto.getEmail().toString());
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
