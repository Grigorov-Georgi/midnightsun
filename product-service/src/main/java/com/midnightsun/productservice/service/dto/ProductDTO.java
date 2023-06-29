package com.midnightsun.productservice.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends AbstractAuditingDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long quantity;

    private BigDecimal weight;

    private Long length;

    private Long width;

    private Long height;

    private CategoryDTO category;
}
