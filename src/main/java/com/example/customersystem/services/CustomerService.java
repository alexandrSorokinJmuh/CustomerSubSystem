package com.example.customersystem.services;

import com.example.customersystem.dao.AddressDao;
import com.example.customersystem.dao.CustomerDao;
import com.example.customersystem.entities.Address;
import com.example.customersystem.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final AddressDao addressDao;

    public CustomerService(CustomerDao customerDao, AddressDao addressDao) {
        this.customerDao = customerDao;
        this.addressDao = addressDao;
    }

    public Customer create(Customer customer){
        return customerDao.create(customer);
    }

    public void testCreateCustomer(){
        // Добавить пользователя
        Customer customer = new Customer();
        customer.setFirstName("asd");
        customer.setLastName("sd");
        customer.setEmail("asdf@asdf.ui");
        customer.setPhone("545454545");
        customer.setPass("asd");
        customer.setAddress(addressDao.getById(1));
        customerDao.create(customer);
    }

    public void testChangeCustomer(){
        // Изменить пользователя
        Customer customer = customerDao.getAll().get(0);
        customer.setFirstName("gf");
        customer.setAddress(addressDao.getById(3));
        customerDao.update(1, customer);
    }

    public void testAddAddress(){
        // Добавить Самару
        Address address = new Address();
        address.setCountry("Россия");
        address.setState("Саратовская область");
        address.setCity("Самара");
        addressDao.create(address);
    }

    public void testDeleteAddress(){
        // Удалить Самару с её жителями
        addressDao.delete(3);
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
}
