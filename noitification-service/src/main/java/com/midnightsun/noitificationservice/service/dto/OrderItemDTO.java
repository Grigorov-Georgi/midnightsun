package com.midnightsun.noitificationservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
