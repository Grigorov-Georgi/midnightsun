package com.midnightsun.productservice.model;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends AbstractAuditingEntity {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long quantity;

    private BigDecimal weight;

    private Long length;

    private Long width;

    private Long height;

    @ManyToOne
    private Category category;
}
