package com.midnightsun.revrateservice.service.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.revrateservice.model.Review;
import com.midnightsun.revrateservice.repository.RatingRepository;
import com.midnightsun.revrateservice.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.midnightsun.revrateservice.config.Constants.*;

@Slf4j
@Service
public class CacheService {

    private final ZSetOperations<String, String> zSetOperations;
    private final ValueOperations<String, String> valueOperations;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CacheService(RedisTemplate<String, String> redisTemplate, RatingRepository ratingRepository, ReviewRepository reviewRepository) {
        this.zSetOperations = redisTemplate.opsForZSet();
        this.valueOperations = redisTemplate.opsForValue();
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
    }

    public void updateProductAverageRatingZSet(UUID id) {
        log.debug("Update rating zset with the average score of product with ID: {}", id);
        final var averageRating = ratingRepository.getAverageRatingByProductId(id);
        zSetOperations.add(ZSET_RATING, id.toString(), averageRating);
    }

    public void updateAllProductAverageRatingZSet() {
        log.debug("Update rating zset with the average scores of all products");
        final var ratingProductIds = ratingRepository.findAllDistinctProductIds();

        if (ratingProductIds == null || ratingProductIds.isEmpty()) return;

        Set<ZSetOperations.TypedTuple<String>> productAvgScoreTuples = new HashSet<>();

        for (UUID productId : ratingProductIds) {
            final var averageRating = ratingRepository.getAverageRatingByProductId(productId);
            productAvgScoreTuples.add(ZSetOperations.TypedTuple.of(productId.toString(), averageRating));
        }

        zSetOperations.add(ZSET_RATING, productAvgScoreTuples);
    }

    public void updateProductReviews(UUID id) {
        log.debug("Update Redis cache with the reviews of product with ID: {}", id);
        final var reviews = reviewRepository.findAllByProductId(id);
        final var reviewsText = reviews.stream().map(Review::getText).collect(Collectors.toList());

        final var key = String.format("%s%s", REVIEW_PREFIX, id);

        try {
            final var value = objectMapper.writeValueAsString(reviewsText);
            updateCache(key, value);
        } catch (JsonProcessingException e) {
            log.debug("Could not parse list of reviews for product with ID {}", id);
        }
    }

    public void updateAllProductReviewsCache() {
        log.debug("Update Redis cache with the reviews of all products");
        final var reviewProductIds = reviewRepository.findAllDistinctProductIds();

        if (reviewProductIds == null || reviewProductIds.isEmpty()) return;

        Map<String, String> productReviewsMap = new HashMap<>();

        for (UUID productId : reviewProductIds) {
            final var reviews = reviewRepository.findAllByProductId(productId);
            final var reviewsText = reviews.stream().map(Review::getText).collect(Collectors.toList());

            final var key = String.format("%s%s", REVIEW_PREFIX, productId);

            try {
                final var value = objectMapper.writeValueAsString(reviewsText);
                productReviewsMap.put(key, value);
            } catch (JsonProcessingException e) {
                log.debug("Could not parse list of reviews for product with ID {}", productId);
            }
        }

        this.updateCache(productReviewsMap);
    }

    public void updateCache(Map<String, String> keyValuePairs) {
        valueOperations.multiSet(keyValuePairs);
    }

    public void updateCache(String key, String value) {
        Map<String, String> keyValuePair = new HashMap<>();
        keyValuePair.put(key, value);
        updateCache(keyValuePair);
    }
}
