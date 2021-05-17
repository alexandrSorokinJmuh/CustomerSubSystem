package com.example.order_sub_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    Integer offer_id;
    String name;
    Float price;
    Integer category;

}