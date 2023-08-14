package com.midnightsun.noitificationservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemExtendedInfoDTO {

    private String name;

    private BigDecimal price;

    private String imageBase64;

    private String description;
}
