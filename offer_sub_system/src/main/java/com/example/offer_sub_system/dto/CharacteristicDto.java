package com.example.offer_sub_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacteristicDto {
    Integer characteristic_id;
    String name;
    String description;
}
