package com.midnightsun.noitificationservice.service.dto;

import com.midnightsun.noitificationservice.service.dto.enums.OrderStatus;
import com.midnightsun.noitificationservice.service.dto.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends AbstractAuditingDTO {

    private UUID id;

    private String customerEmail;

    private OrderStatus status;

    private OrderType type;

    private String street;

    private Long postalCode;

    private CityDTO city;

    private BigDecimal totalPrice;

    private Set<OrderItemDTO> orderItems;
}
