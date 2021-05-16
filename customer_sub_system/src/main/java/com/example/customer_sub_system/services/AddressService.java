package com.example.customer_sub_system.services;

import com.example.customer_sub_system.dao.AddressDao;
import com.example.customer_sub_system.entities.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressDao addressDao;

    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public Address create(Address address){
        return addressDao.create(address);
    }

    public Address update(int id, Address address){
        return addressDao.update(id, address);
    }
    public void delete(int id){
        addressDao.delete(id);
    }
    public List<Address> getAll() {
        return addressDao.getAll();
    }

    public Address getById(int id) {
        return addressDao.getById(id);
    }
}
