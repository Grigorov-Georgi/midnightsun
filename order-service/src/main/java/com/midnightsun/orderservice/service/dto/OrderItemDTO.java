package com.midnightsun.orderservice.service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO extends AbstractAuditingDTO {

    private Long id;

    private UUID productId;

    private Long quantity;

    private OrderItemExtendedInfoDTO orderItemExtendedInfoDTO;
}
