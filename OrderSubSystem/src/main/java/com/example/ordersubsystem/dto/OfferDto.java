package com.example.ordersubsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfferDto {
    Integer offer_id;
    String name;
    Float price;
    Integer category;

}