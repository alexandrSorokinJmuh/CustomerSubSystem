package com.example.customer_sub_system.services;

import com.example.customer_sub_system.dao.AddressDao;
import com.example.customer_sub_system.dao.CustomerDao;
import com.example.customer_sub_system.dao.PaidTypeDao;
import com.example.customer_sub_system.dto.CustomerDto;
import com.example.customer_sub_system.entities.Address;
import com.example.customer_sub_system.entities.Customer;
import com.example.customer_sub_system.entities.Role;
import com.example.customer_sub_system.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final AddressDao addressDao;
    private final PaidTypeDao paidTypeDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao, AddressDao addressDao, PaidTypeDao paidTypeDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.addressDao = addressDao;
        this.paidTypeDao = paidTypeDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer create(Customer customer){
        System.out.println(customer);
        customer.setRole(Role.USER);
        customer.setStatus(Status.ACTIVE);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerDao.create(customer);
    }


    public Customer update(int id, Customer customer){
        return customerDao.update(id, customer);
    }
    public void delete(int id){
        customerDao.delete(id);
    }
    public List<Customer> getAll() {
        return customerDao.getAll();
    }

    public Customer getById(int id) {
        return customerDao.getById(id);
    }

    public Customer updateWithDto(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhone(customerDto.getPhone());
        if (customerDto.getPass() != null && !customerDto.getPass().isEmpty()) {

            customer.setPassword(passwordEncoder.encode(customerDto.getPass()));
        }else {

        }
        if (customerDto.getAddressId() != null && !customerDto.getAddressId().isEmpty())
            customer.setAddress(addressDao.getById(Integer.parseInt(customerDto.getAddressId())));
        if (customerDto.getPaidTypesId() != null)
            customer.setPaidTypes(Arrays.stream(customerDto.getPaidTypesId())
                    .map(paidTypeId -> paidTypeDao.getById(Integer.parseInt(paidTypeId)))
                    .collect(Collectors.toList())
            );
        return this.update(customerDto.getCustomer_id(), customer);
    }

    public Customer getByEmail(String email) {
        System.out.println(email);
        return customerDao.findByEmail(email).get();
    }
}
