package com.midnightsun.orderservice.model;

import com.midnightsun.orderservice.model.enums.OrderStatus;
import com.midnightsun.orderservice.model.enums.OrderType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Enumerated(value = EnumType.STRING)
    private OrderType type;

    private String customerEmail;

    private String street;

    private Long postalCode;

    @ManyToOne
    private City city;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    public void resetOrderItems() {
        this.orderItems = null;
    }
}
