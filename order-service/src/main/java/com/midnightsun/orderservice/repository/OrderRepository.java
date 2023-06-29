package com.midnightsun.orderservice.repository;

import com.midnightsun.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o LEFT JOIN OrderItem oi ON o.id = oi.order.id WHERE o.id = :id")
    Optional<Order> findById(UUID id);
}
