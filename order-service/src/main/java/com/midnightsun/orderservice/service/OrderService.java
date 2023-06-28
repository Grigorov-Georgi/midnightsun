package com.midnightsun.orderservice.service;

import com.midnightsun.orderservice.mapper.OrderMapper;
import com.midnightsun.orderservice.model.Order;
import com.midnightsun.orderservice.repository.OrderRepository;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import com.midnightsun.orderservice.web.exception.HttpBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public Page<OrderDTO> getAll(Pageable pageable) {
        log.debug("Request to get all ORDERS");
        return orderRepository.findAll(pageable).map(orderMapper::toDTO);
    }

    public OrderDTO getOne(UUID uuid) {
        log.debug("Request to get ORDER by ID: {}", uuid);
        return orderMapper.toDTO(orderRepository.findById(uuid).orElse(null));
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
        final var savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    public void delete(UUID uuid) {
        log.debug("Request to delete ORDER with ID: {}", uuid);
        orderRepository.deleteById(uuid);
    }
}
