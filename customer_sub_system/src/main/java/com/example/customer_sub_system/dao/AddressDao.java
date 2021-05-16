package com.example.customer_sub_system.dao;

import com.example.customer_sub_system.entities.Address;
import com.example.customer_sub_system.entities.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class AddressDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Address> getAll() {
        return entityManager.createQuery("FROM Address", Address.class).getResultList();
    }

    public Address getById(int id) {
        return entityManager.find(Address.class, id);
    }

    public Address create(Address address) {
        entityManager.persist(address);
        return address;
    }

    public Address update(int id, Address address) {
        Address original = entityManager.find(Address.class, id);
        if (original != null) {
            original.setCity(address.getCity());
            original.setCountry(address.getCountry());
            original.setState(address.getState());

            original.getCustomerList().clear();
            for (Customer customer : address.getCustomerList()) {
                original.getCustomerList().add(customer);
//                paidType.getCustomerList().add(original);
            }

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        Address address = entityManager.find(Address.class, id);
        if (address != null) {
            entityManager.remove(address);
        }
    }
}
