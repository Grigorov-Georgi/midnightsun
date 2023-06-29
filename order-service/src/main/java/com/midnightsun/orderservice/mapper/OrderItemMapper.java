package com.midnightsun.orderservice.mapper;

import com.midnightsun.orderservice.model.OrderItem;
import com.midnightsun.orderservice.service.dto.OrderItemDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface OrderItemMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    OrderItem toEntity(OrderItemDTO orderItemDTO);

    OrderItemDTO toDTO(OrderItem orderItem);
}
