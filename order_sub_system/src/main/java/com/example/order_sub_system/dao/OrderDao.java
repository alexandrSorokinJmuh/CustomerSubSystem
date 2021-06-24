package com.example.order_sub_system.dao;

import com.example.order_sub_system.entities.Orders;
import org.hibernate.criterion.Order;
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
    public void deleteByCustomerId(int id) {
        List<?> resultList;
        resultList = entityManager.createQuery("SELECT orders from Orders orders " +
                "where orders.customer_id = ?1")
                .setParameter(1, id)
                .getResultList();

        if (resultList.size() > 0) {
            for (Object order : resultList) {

                entityManager.remove( order);
            }
        }
    }

    public void deleteByOfferId(int id) {
        List<?> resultList;
        System.out.println("id");
        resultList = entityManager.createQuery("SELECT orders from Orders orders " +
                "where orders.offer_id = ?1")
                .setParameter(1, id)
                .getResultList();
        System.out.println(resultList);
        if (resultList.size() > 0) {
            for (Object order : resultList)
                entityManager.remove(order);
        }
    }

    public void delete(int id) {
        Orders order = entityManager.find(Orders.class, id);

        if (order != null) {
            entityManager.remove(order);
        }
    }
}
