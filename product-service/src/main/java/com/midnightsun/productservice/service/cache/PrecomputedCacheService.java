package com.midnightsun.productservice.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.midnightsun.productservice.config.Constants.REVIEW_PREFIX;
import static com.midnightsun.productservice.config.Constants.ZSET_RATING;

@Slf4j
@Service
public class PrecomputedCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ZSetOperations<String, String>  zSetOperations;
    private final ObjectMapper objectMapper;

    public PrecomputedCacheService(RedisTemplate<String, String> redisTemplate,
                                   ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
        this.objectMapper = objectMapper;
    }

    public Double getProductRatingScore(UUID id) {
        final var score = zSetOperations.score(ZSET_RATING, id);
        return score != null ? score : 0;
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

    public Set<UUID> getIdOfTopRatedProducts(Long n) {
        Long zSetSize = redisTemplate.opsForZSet().zCard(ZSET_RATING);
        Long start = 0L;
        Long end = n - 1;

        if (zSetSize == null) return new HashSet<>();

        if (zSetSize < end) {
            end = zSetSize - 1;
        }

        return Objects.requireNonNull(zSetOperations.reverseRange(ZSET_RATING, start, end))
                .stream().map(UUID::fromString)
                .collect(Collectors.toSet());
    }
}
