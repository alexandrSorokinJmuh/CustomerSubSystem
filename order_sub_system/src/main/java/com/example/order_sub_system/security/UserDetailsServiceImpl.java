package com.example.order_sub_system.security;

import com.example.order_sub_system.dto.CustomerDto;
import com.example.order_sub_system.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final OrderService orderService;

    @Autowired
    public UserDetailsServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerDto customerDto = orderService.getCustomerByEmail(email);


        return SecurityUser.fromCustomerToUserDetails(customerDto);
    }
}
