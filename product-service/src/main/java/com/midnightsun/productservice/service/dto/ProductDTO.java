package com.midnightsun.productservice.service.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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

    private Double ratingScore;

    private List<String> reviews;

    private boolean dirty = false;

    public void markAsDirty() {
        this.dirty = true;
    }

    public boolean isDirty() {
        return this.dirty;
    }
}
