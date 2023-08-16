package com.midnightsun.orderservice.mapper;

import com.midnightsun.orderservice.model.Order;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, CityMapper.class})
public interface OrderMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Order toEntity(OrderDTO orderDTO);

    @Mapping(source = "orderItems", target = "orderItems")
    OrderDTO toDTO(Order order);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoId(Order order);
}
