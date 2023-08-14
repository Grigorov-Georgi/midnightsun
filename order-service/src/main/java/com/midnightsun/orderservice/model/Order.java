package com.midnightsun.orderservice.model;

import com.midnightsun.orderservice.model.enums.OrderStatus;
import com.midnightsun.orderservice.model.enums.OrderType;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order extends AbstractAuditingEntity implements Serializable {

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

    private BigDecimal totalPrice;

    @ManyToOne
    private City city;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    public void resetOrderItems() {
        this.orderItems = null;
    }

    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", customerEmail='" + customerEmail + '\'' +
                ", street='" + street + '\'' +
                ", postalCode=" + postalCode +
                ", city=" + city +
                ", orderItems=" + orderItems +
                '}';
    }
}
