package com.example.customer_sub_system.services;

import com.example.customer_sub_system.dao.AddressDao;
import com.example.customer_sub_system.dao.CustomerDao;
import com.example.customer_sub_system.dao.PaidTypeDao;
import com.example.customer_sub_system.dto.CustomerDto;
import com.example.customer_sub_system.entities.Address;
import com.example.customer_sub_system.entities.Customer;
import com.example.customer_sub_system.entities.Role;
import com.example.customer_sub_system.entities.Status;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final AddressDao addressDao;
    private final PaidTypeDao paidTypeDao;

    public CustomerService(CustomerDao customerDao, AddressDao addressDao, PaidTypeDao paidTypeDao) {
        this.customerDao = customerDao;
        this.addressDao = addressDao;
        this.paidTypeDao = paidTypeDao;
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
        customer.setPassword("asd");
        customer.setRole(Role.USER);
        customer.setStatus(Status.ACTIVE);

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

    public Customer updateWithDto(CustomerDto customerDto) {
        Customer customer = new Customer();

        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhone(customerDto.getPhone());
        customer.setPassword(customerDto.getPass());

        if (customerDto.getAddressId() != null && !customerDto.getAddressId().isEmpty())
            customer.setAddress(addressDao.getById(Integer.parseInt(customerDto.getAddressId())));
        if (customerDto.getPaidTypesId() != null)
            customer.setPaidTypes(Arrays.stream(customerDto.getPaidTypesId())
                    .map(paidTypeId -> paidTypeDao.getById(Integer.parseInt(paidTypeId)))
                    .collect(Collectors.toList())
            );
        return this.update(customerDto.getCustomer_id(), customer);
    }
}
