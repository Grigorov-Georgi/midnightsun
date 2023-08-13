package com.midnightsun.orderservice.web;

import com.midnightsun.orderservice.service.OrderService;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAll(Pageable pageable) {
        log.debug("REST request to get all ORDERS sorted by {}, page number: {} and page size: {}",
                pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());
        final var orders = orderService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOne(@PathVariable UUID id, @RequestParam(defaultValue = "false") boolean withFullInfo) {
        log.debug("REST request to get ORDER by ID: {}", id);
        final var city = orderService.getOne(id, withFullInfo);
        return ResponseEntity.status(HttpStatus.OK).body(city);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> save(@RequestBody OrderDTO orderDTO) {
        log.debug("REST request to save ORDER with content: {}", orderDTO);
        final var savedOrder = orderService.save(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping
    public ResponseEntity<OrderDTO> update(@RequestBody OrderDTO orderDTO) {
        log.debug("REST request to updated ORDER with ID: {} with content {}", orderDTO.getId(), orderDTO);
        final var updatedCity = orderService.update(orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.debug("REST request to delete ORDER with ID: {}", id);
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
