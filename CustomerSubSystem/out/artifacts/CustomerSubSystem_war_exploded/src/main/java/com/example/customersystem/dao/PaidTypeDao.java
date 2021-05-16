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
public class PaidTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PaidType> getAll() {
        return entityManager.createQuery("FROM PaidType", PaidType.class).getResultList();
    }

    public PaidType getById(int id) {
        return entityManager.find(PaidType.class, id);
    }

    public PaidType create(PaidType paidType) {
        entityManager.persist(paidType);
        return paidType;
    }

    public PaidType update(int id, PaidType paidType) {
        PaidType original = entityManager.find(PaidType.class, id);
        if (original != null) {
            original.setName(paidType.getName());

            original.getCustomerList().clear();
            for (Customer customer : paidType.getCustomerList()) {
                original.getCustomerList().add(customer);
//                paidType.getCustomerList().add(original);
            }

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        PaidType paidType = entityManager.find(PaidType.class, id);
        if (paidType != null) {
            entityManager.remove(paidType);
        }
    }

}
