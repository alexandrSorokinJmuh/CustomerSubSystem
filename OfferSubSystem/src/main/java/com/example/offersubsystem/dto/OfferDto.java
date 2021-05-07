package com.example.offersubsystem.dto;

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
    Integer[] characteristicValues;

}
