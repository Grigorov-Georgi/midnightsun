package com.midnightsun.orderservice.service;

import com.midnightsun.orderservice.service.cache.ProductInfoService;
import com.midnightsun.orderservice.service.rabbitmq.producer.NotificationProducer;
import com.midnightsun.orderservice.mapper.OrderMapper;
import com.midnightsun.orderservice.model.Order;
import com.midnightsun.orderservice.model.OrderItem;
import com.midnightsun.orderservice.repository.OrderItemRepository;
import com.midnightsun.orderservice.repository.OrderRepository;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import com.midnightsun.orderservice.web.exception.HttpBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final NotificationProducer notificationProducer;
    private final ProductInfoService productInfoService;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        OrderMapper orderMapper,
                        NotificationProducer notificationProducer, ProductInfoService productInfoService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.notificationProducer = notificationProducer;
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
        final var order = orderMapper.toEntity(orderDTO);
        return save(order);
    }

    public OrderDTO update(OrderDTO orderDTO) {
        log.debug("Request to update ORDER: {}", orderDTO);
        if (orderDTO.getId() == null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        }
        final var order = orderMapper.toEntity(orderDTO);
        return save(order);
    }

    private OrderDTO save(Order order) {
        final Set<OrderItem> orderItemSet = order.getOrderItems();
        order.resetOrderItems();

        final var savedOrder = orderRepository.save(order);

        if (orderItemSet != null && !orderItemSet.isEmpty()) {
            orderItemSet.forEach(orderItem -> orderItem.setOrder(savedOrder));
            final var savedOrderItems = orderItemRepository.saveAll(orderItemSet);
            savedOrder.setOrderItems(Set.copyOf(savedOrderItems));
        }

        final var savedOrderDTO = orderMapper.toDTO(savedOrder);

        notificationProducer.sendEmailForOrderCreation(savedOrderDTO);

        return savedOrderDTO;
    }

    public void delete(UUID uuid) {
        log.debug("Request to delete ORDER with ID: {}", uuid);
        orderRepository.deleteById(uuid);
    }
}
