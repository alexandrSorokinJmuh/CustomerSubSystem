package com.example.offer_sub_system.security;

import com.example.offer_sub_system.dto.CustomerDto;
import com.example.offer_sub_system.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final OfferService offerService;

    @Autowired
    public UserDetailsServiceImpl(OfferService offerService) {
        this.offerService = offerService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerDto customerDto = offerService.getCustomerByEmail(email);


        return SecurityUser.fromCustomerToUserDetails(customerDto);
    }
}
