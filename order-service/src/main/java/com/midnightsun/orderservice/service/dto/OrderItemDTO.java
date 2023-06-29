package com.midnightsun.orderservice.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO extends AbstractAuditingDTO {

    private Long id;

    private Long productId;

    private Long quantity;
}
