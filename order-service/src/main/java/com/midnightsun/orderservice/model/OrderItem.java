package com.midnightsun.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator = "order_item_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_item_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    private Long productId;

    private Long quantity;

    @ManyToOne
    private Order order;
}
