package com.example.customersystem.dao;


import com.example.customersystem.entities.Customer;
import com.example.customersystem.entities.PaidType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CustomerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Customer> getAll() {
        return entityManager.createQuery("from Customer", Customer.class).getResultList();
    }

    public Customer getById(int id) {
        return entityManager.find(Customer.class, id);
    }

    public Customer getByEmail(String email){
        return entityManager.find(Customer.class, email);
    }

    public Customer create(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    public Customer update(int id, Customer customer) {
        Customer original = entityManager.find(Customer.class, id);
        if (original != null) {
            original.setAddress(customer.getAddress());
            original.setPass(customer.getPass());

            original.setEmail(customer.getEmail());
            original.setPhone(customer.getPhone());

            original.setFirstName(customer.getFirstName());
            original.setLastName(customer.getLastName());


            for(PaidType paidType : original.getPaidTypes())
                paidType.getCustomerList().remove(original);

            original.getPaidTypes().clear();
            for (PaidType paidType : customer.getPaidTypes()) {
                original.getPaidTypes().add(paidType);

                paidType.getCustomerList().add(original);
            }

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer != null) {
            entityManager.remove(customer);
        }
    }
}
