package com.midnightsun.orderservice.mapper;

import com.midnightsun.orderservice.config.security.SecurityUtils;
import com.midnightsun.orderservice.model.Order;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, CityMapper.class})
public interface OrderMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "customerEmail", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Order toEntity(OrderDTO orderDTO);

    @Mapping(source = "orderItems", target = "orderItems")
    OrderDTO toDTO(Order order);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoId(Order order);

    @AfterMapping
    default void setCustomerEmail(@MappingTarget Order order) {
        String currentUser = SecurityUtils.getCurrentLoggedUser().get();
        order.setCustomerEmail(currentUser);
    }
}
