package com.midnightsun.orderservice.service.dto;

import com.midnightsun.orderservice.model.enums.OrderStatus;
import com.midnightsun.orderservice.model.enums.OrderType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends AbstractAuditingDTO {

    private UUID id;

    //TODO: This will be changed when authentication is implemented
    private String customerEmail;

    //TODO: Statuses needs to be changed from saga pattern orchestrator -> they will be ignored when saving order
    private OrderStatus status;

    private OrderType type;

    private String street;

    private Long postalCode;

    private CityDTO city;

    private BigDecimal totalPrice;

    private Set<OrderItemDTO> orderItems;
}
