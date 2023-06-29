package com.midnightsun.productservice.model;

import javax.persistence.*;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "product_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

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
