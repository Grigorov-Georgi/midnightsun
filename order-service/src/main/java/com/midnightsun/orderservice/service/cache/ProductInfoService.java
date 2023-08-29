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

    public OrderDTO getExtendedProductInfo(OrderDTO orderDTO) {
        List<UUID> productIds = orderDTO.getOrderItems().stream().map(OrderItemDTO::getProductId).collect(Collectors.toList());

        List<String> productKeysForRedis = productIds.stream().map(this::buildKey).collect(Collectors.toList());
        List<String> cachedProducts = redisTemplate.opsForValue().multiGet(productKeysForRedis);

        Map<UUID, OrderItemExtendedInfoDTO> resultMap = new HashMap<>();

        List<UUID> unavailableInCacheProductIds = findUnavailableProductIds(productIds, cachedProducts, resultMap);

        if (!unavailableInCacheProductIds.isEmpty()) {
            fetchAndCacheProductInfo(unavailableInCacheProductIds, resultMap);
        }

        updateOrderItemsWithExtendedInfo(orderDTO, resultMap);

        return orderDTO;
    }

    private List<UUID> findUnavailableProductIds(List<UUID> productIds,
                                                 List<String> cachedProducts,
                                                 Map<UUID, OrderItemExtendedInfoDTO> resultMap) {
        List<UUID> unavailableInCacheProductIds = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            UUID currentProductId = productIds.get(i);
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

        log.debug("CACHE HITS: {}, CACHE MISSES: {}",
                productIds.size() - unavailableInCacheProductIds.size(),
                unavailableInCacheProductIds.size());

        return unavailableInCacheProductIds;
    }

    private void fetchAndCacheProductInfo(List<UUID> unavailableInCacheProductIds, Map<UUID, OrderItemExtendedInfoDTO> resultMap) {
        final var originalMap = externalProductService.getProductsInfo(unavailableInCacheProductIds);

        if (originalMap == null) return;

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

    private void updateOrderItemsWithExtendedInfo(OrderDTO orderDTO, Map<UUID, OrderItemExtendedInfoDTO> resultMap) {
        orderDTO.getOrderItems().forEach(o -> {
            final var currentProductId = o.getProductId();
            OrderItemExtendedInfoDTO currentExtendedInfo = resultMap.get(currentProductId);

            if (currentExtendedInfo != null) {
                o.setOrderItemExtendedInfoDTO(currentExtendedInfo);
            }
        });
    }

    private String buildKey(UUID id) {
        return String.format("%s%s", PRODUCT_PREFIX, id);
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
