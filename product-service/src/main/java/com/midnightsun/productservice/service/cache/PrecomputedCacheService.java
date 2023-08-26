package com.midnightsun.productservice.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PrecomputedCacheService {

    private static final String RATING_PREFIX = "rev-rate:rating:";
    private static final String REVIEW_PREFIX = "rev-rate:review:";

    private RedisTemplate<String, String> redisTemplate;

    public PrecomputedCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Double getProductRatingScore(Long id) {
        final var key = String.format("%s%d", RATING_PREFIX, id);
        final var rating = redisTemplate.opsForValue().get(key);
        return rating != null ? Double.parseDouble(rating) : 0;
    }

    public List<String> getProductReviews(Long id) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        final var key = String.format("%s%d", REVIEW_PREFIX, id);
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
