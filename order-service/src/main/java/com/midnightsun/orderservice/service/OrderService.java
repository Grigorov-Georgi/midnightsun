package com.midnightsun.orderservice.service;

import com.midnightsun.orderservice.service.cache.ProductInfoService;
import com.midnightsun.orderservice.service.event.OrderCreatedEvent;
import com.midnightsun.orderservice.mapper.OrderMapper;
import com.midnightsun.orderservice.repository.OrderItemRepository;
import com.midnightsun.orderservice.repository.OrderRepository;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import com.midnightsun.orderservice.web.exception.HttpBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ProductInfoService productInfoService;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        OrderMapper orderMapper,
                        ApplicationEventPublisher applicationEventPublisher,
                        ProductInfoService productInfoService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.productInfoService = productInfoService;
    }

    @Transactional
    public Page<OrderDTO> getAll(Pageable pageable) {
        log.debug("Request to get all ORDERS");
        return orderRepository.findAll(pageable).map(orderMapper::toDTO);
    }

    @Transactional
    public OrderDTO getOne(UUID uuid, boolean withFullInfo) {
        log.debug("Request to get ORDER by ID: {}", uuid);

        var orderDTO = orderRepository.findById(uuid)
                .map(orderMapper::toDTO)
                .orElse(null);

        if (orderDTO == null) return null;

        if (withFullInfo){
            return productInfoService.getExtendedProductInfo(orderDTO);
        }

        return orderDTO;
    }

    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save ORDER: {}", orderDTO);
        if (orderDTO.getId() != null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        }
        orderDTO = setTotalPrice(orderDTO);

        final var savedOrder = saveOrderAndOrderItems(orderDTO);
        savedOrder.setOrderItems(orderDTO.getOrderItems());

        publishOrderCreatedEvent(savedOrder);

        return savedOrder;
    }

    public void delete(UUID uuid) {
        log.debug("Request to delete ORDER with ID: {}", uuid);
        orderRepository.deleteById(uuid);
    }

    //Here the invocation of extended products info is used to get their prices and calculate the total price.
    //Creation of an order is always followed by fetching of it. In order to optimize the process
    //extended products information will be cached and directly pulled from the cache when needed.
    private OrderDTO setTotalPrice(OrderDTO orderDTO) {
        orderDTO = productInfoService.getExtendedProductInfo(orderDTO);
        orderDTO.setTotalPrice(calculateTotalPrice(orderDTO));
        return orderDTO;
    }

    private BigDecimal calculateTotalPrice(OrderDTO order) {
        return order.getOrderItems()
                .stream()
                .map(item -> {
                    final var price = item.getOrderItemExtendedInfoDTO().getPrice();
                    final var quantity = BigDecimal.valueOf(item.getQuantity());
                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderDTO saveOrderAndOrderItems(OrderDTO orderDTO){
        final var order = orderMapper.toEntity(orderDTO);

        final var orderItemSet = order.getOrderItems();
        order.resetOrderItems();

        final var savedOrder = orderRepository.save(order);

        if (orderItemSet != null && !orderItemSet.isEmpty()) {
            orderItemSet.forEach(orderItem -> orderItem.setOrder(savedOrder));
            final var savedOrderItems = orderItemRepository.saveAll(orderItemSet);
            savedOrder.setOrderItems(Set.copyOf(savedOrderItems));
        }

        return orderMapper.toDTO(savedOrder);
    }

    private void publishOrderCreatedEvent(OrderDTO orderDTO) {
        OrderCreatedEvent event = new OrderCreatedEvent(this, orderDTO);
        applicationEventPublisher.publishEvent(event);
    }
}
