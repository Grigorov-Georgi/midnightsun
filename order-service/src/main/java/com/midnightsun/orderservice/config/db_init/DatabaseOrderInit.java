package com.midnightsun.orderservice.config.db_init;

import com.midnightsun.orderservice.model.City;
import com.midnightsun.orderservice.model.Order;
import com.midnightsun.orderservice.model.OrderItem;
import com.midnightsun.orderservice.model.enums.OrderStatus;
import com.midnightsun.orderservice.model.enums.OrderType;
import com.midnightsun.orderservice.repository.CityRepository;
import com.midnightsun.orderservice.repository.OrderItemRepository;
import com.midnightsun.orderservice.repository.OrderRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseOrderInit implements ApplicationRunner {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CityRepository cityRepository;

    public DatabaseOrderInit(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CityRepository cityRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (orderRepository.count() == 0 && orderItemRepository.count() == 0) {
            List<City> cities = cityRepository.findAll();

            for (int i = 0; i < 5; i++) {
                Order order = new Order();
                order.setCustomerEmail("ggrigorov@mail.com");
                order.setCity(cities.get(i));
                order.setStatus(OrderStatus.PENDING);
                order.setType(OrderType.STANDARD);
                order.setStreet("bl. Cherni Vrah 51");
                order.setPostalCode(1000L);

                order = orderRepository.save(order);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setQuantity(Long.valueOf(i) + 5L);
                orderItem.setProductId(Long.valueOf(i) + 1000L);

                orderItemRepository.save(orderItem);
            }
        }
    }
}
