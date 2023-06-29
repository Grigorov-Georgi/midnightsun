package com.midnightsun.orderservice.model;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
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

    @ManyToOne(optional = false)
    private Order order;


    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
