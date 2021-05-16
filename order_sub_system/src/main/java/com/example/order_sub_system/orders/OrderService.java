package com.example.order_sub_system.orders;

import com.example.order_sub_system.dao.OrderDao;
import com.example.order_sub_system.dto.OrdersDto;
import com.example.order_sub_system.entities.Orders;
import com.example.order_sub_system.entities.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Orders> getAll() {
        return orderDao.getAll();
    }

    public Orders getById(int id) {
        return orderDao.getById(id);
    }

    public Orders create(Orders order){

        return orderDao.create(order);
    }

    public Orders update(int id, Orders order){
        return orderDao.update(id, order);
    }

    public void delete(int id){
        orderDao.delete(id);
    }

    public Orders updateStatus(int id, OrdersDto ordersDto) {
        Orders orders = new Orders();
        Orders ordersOld = orderDao.getById(id);
        orders.setStatus(Status.valueOf(ordersDto.getStatus()));
        orders.setCustomer_id(ordersOld.getCustomer_id());
        orders.setPaid(ordersOld.isPaid());
        orders.setOffer_id(ordersOld.getOffer_id());
        orders.setName(ordersOld.getName());
        orders.setDeliveryTime(ordersOld.getDeliveryTime());

        return orderDao.update(ordersDto.getOrder_id(), orders);
    }

    public Orders updateWithDto(OrdersDto ordersDto) {
        Orders orders = new Orders();
        orders.setCustomer_id(ordersDto.getCustomer_id());
        orders.setStatus(Status.valueOf(ordersDto.getStatus()));
        orders.setPaid(ordersDto.isPaid());
        orders.setOffer_id(ordersDto.getOffer_id());
        orders.setName(ordersDto.getName());
        orders.setDeliveryTime(ordersDto.getDeliveryTime());

        return orderDao.update(ordersDto.getOrder_id(), orders);
    }
}
