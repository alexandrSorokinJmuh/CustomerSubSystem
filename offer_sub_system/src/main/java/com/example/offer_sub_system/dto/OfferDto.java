package com.example.offer_sub_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfferDto {
    Integer offer_id;
    String name;
    Float price;
    Integer category;
    Integer[] paidTypes;
    Integer[] characteristics;
    String[] characteristicValues;

}
