package com.example.ordersubsystem.dto;

import lombok.Data;

@Data
public class LabelAndValueDto {
    private String label;
    private String value;

    public LabelAndValueDto() {
    }

    public LabelAndValueDto(String label, String value) {
        this.label = label;
        this.value = value;
    }

}
