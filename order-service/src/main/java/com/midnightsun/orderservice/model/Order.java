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

    private String customerEmail;

    private OrderStatus status;

    private OrderType type;

    private String street;

    private Long postalCode;

    @ManyToOne
    private City city;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;
}
