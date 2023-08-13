package com.midnightsun.orderservice.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import com.midnightsun.orderservice.service.dto.OrderItemDTO;
import com.midnightsun.orderservice.service.dto.OrderItemExtendedInfoDTO;
import com.midnightsun.orderservice.service.rabbitmq.rpc.ExternalProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductInfoService {

    private static final String PRODUCT_PREFIX = "product:product:";

    private final ExternalProductService externalProductService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductInfoService(ExternalProductService externalProductService, RedisTemplate<String, String> redisTemplate) {
        this.externalProductService = externalProductService;
        this.redisTemplate = redisTemplate;
    }

    public OrderDTO getDetailedOrder(OrderDTO orderDTO) {
        List<Long> productIds = orderDTO.getOrderItems().stream().map(OrderItemDTO::getProductId).collect(Collectors.toList());

        List<String> productKeysForRedis = productIds.stream().map(this::buildKey).collect(Collectors.toList());
        List<String> cachedProducts = redisTemplate.opsForValue().multiGet(productKeysForRedis);

        Map<Long, OrderItemExtendedInfoDTO> resultMap = new HashMap<>();

        List<Long> unavailableInCacheProductIds = findUnavailableProductIds(productIds, cachedProducts, resultMap);

        if (!unavailableInCacheProductIds.isEmpty()) {
            fetchAndCacheProductInfo(unavailableInCacheProductIds, resultMap);
        }

        updateOrderItemsWithExtendedInfo(orderDTO, resultMap);

        return orderDTO;
    }

    private List<Long> findUnavailableProductIds(List<Long> productIds,
                                                 List<String> cachedProducts,
                                                 Map<Long, OrderItemExtendedInfoDTO> resultMap) {
        List<Long> unavailableInCacheProductIds = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Long currentProductId = productIds.get(i);
            String currentCachedProductInfo = cachedProducts.get(i);

            if (currentCachedProductInfo == null) {
                unavailableInCacheProductIds.add(currentProductId);
            } else {
                try {
                    OrderItemExtendedInfoDTO extendedInfoDTO = objectMapper.readValue(currentCachedProductInfo, OrderItemExtendedInfoDTO.class);
                    resultMap.put(currentProductId, extendedInfoDTO);
                } catch (JsonProcessingException e) {
                    log.error("Error processing the OrderItemExtendedInfoDTO for productId: {}", currentProductId);
                    //Invalid entry in the cache will be deleted from cache and added to the unavailableInCacheProductIds list
                    unavailableInCacheProductIds.add(currentProductId);
                    redisTemplate.delete(buildKey(currentProductId));
                }
            }
        }

        return unavailableInCacheProductIds;
    }

    private void fetchAndCacheProductInfo(List<Long> unavailableInCacheProductIds, Map<Long, OrderItemExtendedInfoDTO> resultMap) {
        final var originalMap = externalProductService.getProductsInfo(unavailableInCacheProductIds);
        final var transformedMap = originalMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> buildKey(entry.getKey()),
                        entry -> stringifyValue(entry.getValue()),
                        (existingValue, newValue) -> newValue,
                        HashMap::new
                ));
        resultMap.putAll(originalMap);
        redisTemplate.opsForValue().multiSet(transformedMap);
    }

    private void updateOrderItemsWithExtendedInfo(OrderDTO orderDTO, Map<Long, OrderItemExtendedInfoDTO> resultMap) {
        orderDTO.getOrderItems().forEach(o -> {
            final var currentProductId = o.getProductId();
            OrderItemExtendedInfoDTO currentExtendedInfo = resultMap.get(currentProductId);

            if (currentExtendedInfo != null) {
                o.setOrderItemExtendedInfoDTO(currentExtendedInfo);
            }
        });
    }

    private String buildKey(Long id) {
        return String.format("%s%d", PRODUCT_PREFIX, id);
    }

    private String stringifyValue(OrderItemExtendedInfoDTO orderItemExtendedInfoDTO) {
        try {
            return objectMapper.writeValueAsString(orderItemExtendedInfoDTO);
        } catch (JsonProcessingException e) {
            log.error("Error processing the OrderItemExtendedInfoDTO: {}", orderItemExtendedInfoDTO);
            return null;
        }
    }
}
