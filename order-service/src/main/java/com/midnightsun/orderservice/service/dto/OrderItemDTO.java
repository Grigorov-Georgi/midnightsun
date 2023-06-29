package com.midnightsun.orderservice.service.dto;

import com.midnightsun.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;

    private Long productId;

    private Long quantity;
}
