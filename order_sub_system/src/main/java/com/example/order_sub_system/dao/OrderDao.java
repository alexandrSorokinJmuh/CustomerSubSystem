package com.example.order_sub_system.dao;

import com.example.order_sub_system.entities.Orders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class OrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Orders> getAll() {
        return entityManager.createQuery("from Orders", Orders.class).getResultList();
    }

    public Orders getById(int id) {
        return entityManager.find(Orders.class, id);
    }

    public Orders create(Orders order) {
        entityManager.persist(order);
        return order;
    }

    public Orders update(int id, Orders order) {
        Orders original = entityManager.find(Orders.class, id);
        if (original != null) {

            original.setName(order.getName());
            original.setCustomer_id(order.getCustomer_id());
            original.setDeliveryTime(order.getDeliveryTime());
            original.setOffer_id(order.getOffer_id());
            original.setPaid(order.isPaid());
            original.setStatus(order.getStatus());

            entityManager.merge(original);

        }
        return original;
    }

    public void delete(int id) {
        Orders order = entityManager.find(Orders.class, id);

        if (order != null) {
            entityManager.remove(order);
        }
    }
}
