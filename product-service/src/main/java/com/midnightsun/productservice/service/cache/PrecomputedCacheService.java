package com.midnightsun.productservice.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PrecomputedCacheService {

    private static final String RATING_PREFIX = "rev-rate:rating:";
    private static final String REVIEW_PREFIX = "rev-rate:review:";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public PrecomputedCacheService(RedisTemplate<String, String> redisTemplate,
                                   ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public Double getProductRatingScore(UUID id) {
        final var key = String.format("%s%s", RATING_PREFIX, id);
        final var rating = redisTemplate.opsForValue().get(key);
        return rating != null ? Double.parseDouble(rating) : 0;
    }

    public List<String> getProductReviews(UUID id) {
        final var key = String.format("%s%s", REVIEW_PREFIX, id);
        final var serializedReviews = redisTemplate.opsForValue().get(key);

        if (serializedReviews == null) {
            return null;
        }

        try {
            return objectMapper.readValue(serializedReviews, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Could not process reviews from redis for product with ID {}", id);
            log.error("Deleting inconsistent data for product with ID {}", id);
            redisTemplate.delete(key);
            return null;
        }
    }
}
