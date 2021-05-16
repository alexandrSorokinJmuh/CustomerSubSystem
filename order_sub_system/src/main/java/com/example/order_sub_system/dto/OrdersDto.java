package com.example.order_sub_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class OrdersDto {
    private Integer order_id;

    private Integer offer_id;
    private Integer customer_id;

    String name;
    Date deliveryTime;
    private String status;
    boolean paid;
}
