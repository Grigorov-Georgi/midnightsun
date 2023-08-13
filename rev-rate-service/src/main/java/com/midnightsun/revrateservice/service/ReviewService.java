package com.midnightsun.revrateservice.service;

import com.midnightsun.revrateservice.mapper.ReviewMapper;
import com.midnightsun.revrateservice.model.Review;
import com.midnightsun.revrateservice.repository.ReviewRepository;
import com.midnightsun.revrateservice.service.dto.ReviewDTO;
import com.midnightsun.revrateservice.service.redis.CacheService;
import com.midnightsun.revrateservice.web.exception.HttpBadRequestException;
import com.midnightsun.revrateservice.web.exception.HttpNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CacheService cacheService;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, CacheService cacheService) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.cacheService = cacheService;
    }

    public Page<ReviewDTO> getAll(Pageable pageable) {
        log.debug("Request to get all REVIEWS");
        return reviewRepository.findAll(pageable).map(reviewMapper::toDTO);
    }

    public ReviewDTO getOne(Long id) {
        log.debug("Request to get REVIEW by ID: {}", id);
        return reviewMapper.toDTO(reviewRepository.findById(id).orElse(null));
    }

    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Request to save REVIEW: {}", reviewDTO);
        if (reviewDTO.getId() != null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        }
        final var review = reviewMapper.toEntity(reviewDTO);
        return save(review);
    }

    public ReviewDTO update(ReviewDTO reviewDTO) {
        log.debug("Request to update REVIEW: {}", reviewDTO);
        if (reviewDTO.getId() == null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        }
        final var review = reviewMapper.toEntity(reviewDTO);
        return save(review);
    }

    private ReviewDTO save(Review review) {
        final var savedReview = reviewRepository.save(review);
        cacheService.updateProductReviews(review.getProductId());
        return reviewMapper.toDTO(savedReview);
    }

    public void delete(Long id) {
        log.debug("Request to delete REVIEW with ID: {}", id);

        final var review = reviewRepository.findById(id);

        if (review.isEmpty()) {
            throw new HttpNotFoundException("Entity not found");
        }

        final var productId = review.get().getProductId();

        reviewRepository.deleteById(id);
        cacheService.updateProductReviews(productId);
    }
}
