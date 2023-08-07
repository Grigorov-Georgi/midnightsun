package com.midnightsun.revrateservice.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.revrateservice.model.Review;
import com.midnightsun.revrateservice.repository.RatingRepository;
import com.midnightsun.revrateservice.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CacheService {

    private static final String RATING_PREFIX = "rating_";
    private static final String REVIEW_PREFIX = "review_";

    private final RedisTemplate<String, String> redisTemplate;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CacheService(RedisTemplate<String, String> redisTemplate, RatingRepository ratingRepository, ReviewRepository reviewRepository) {
        this.redisTemplate = redisTemplate;
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
    }

    public void updateProductAverageRating(Long id) {
        log.debug("Update Redis cache with the average score of product with ID: {}", id);
        final var averageRating = ratingRepository.getAverageRatingByProductId(id);
        final var key = String.format("%s%d", RATING_PREFIX, id);
        final var value = String.valueOf(averageRating);
        this.updateCache(key, value);
    }

    public void updateAllProductAverageScoreCache() {
        log.debug("Update Redis cache with the average scores of all products");
        final var ratingProductIds = ratingRepository.findAllDistinctProductIds();
        Map<String, String> productRatingsMap = new HashMap<>();

        for (Long productId : ratingProductIds) {
            final var averageRating = ratingRepository.getAverageRatingByProductId(productId);
            final var key = String.format("%s%d", RATING_PREFIX, productId);
            final var value = String.valueOf(averageRating);
            productRatingsMap.put(key, value);
        }

        this.updateCache(productRatingsMap);
    }

    public void updateProductReviews(Long id) {
        log.debug("Update Redis cache with the reviews of product with ID: {}", id);
        final var reviews = reviewRepository.findAllByProductId(id);
        final var reviewsText = reviews.stream().map(Review::getText).collect(Collectors.toList());

        final var key = String.format("%s%d", REVIEW_PREFIX, id);

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
        Map<String, String> productReviewsMap = new HashMap<>();

        for (Long productId : reviewProductIds) {
            final var reviews = reviewRepository.findAllByProductId(productId);
            final var reviewsText = reviews.stream().map(Review::getText).collect(Collectors.toList());

            final var key = String.format("%s%d", REVIEW_PREFIX, productId);

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
        redisTemplate.opsForValue().multiSet(keyValuePairs);
    }

    public void updateCache(String key, String value) {
        Map<String, String> keyValuePair = new HashMap<>();
        keyValuePair.put(key, value);
        updateCache(keyValuePair);
    }
}
