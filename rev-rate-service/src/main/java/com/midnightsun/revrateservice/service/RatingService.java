package com.midnightsun.revrateservice.service;

import com.midnightsun.revrateservice.mapper.RatingMapper;
import com.midnightsun.revrateservice.model.Rating;
import com.midnightsun.revrateservice.repository.RatingRepository;
import com.midnightsun.revrateservice.service.dto.RatingDTO;
import com.midnightsun.revrateservice.service.redis.CacheService;
import com.midnightsun.revrateservice.web.exception.HttpBadRequestException;
import com.midnightsun.revrateservice.web.exception.HttpNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    public Page<RatingDTO> getAll(Pageable pageable) {
        log.debug("Request to get all RATINGS");
        return ratingRepository.findAll(pageable).map(ratingMapper::toDTO);
    }

    public RatingDTO getOne(Long id) {
        log.debug("Request to get RATING by ID: {}", id);
        return ratingMapper.toDTO(ratingRepository.findById(id).orElse(null));
    }

    public RatingDTO save(RatingDTO ratingDTO) {
        log.debug("Request to save RATING: {}", ratingDTO);
        if (ratingDTO.getId() != null) throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        final var rating = ratingMapper.toEntity(ratingDTO);
        return saveEntity(rating);
    }

    public RatingDTO update(RatingDTO ratingDTO) {
        log.debug("Request to update RATING: {}", ratingDTO);
        if (ratingDTO.getId() == null) throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        final var rating = ratingMapper.toEntity(ratingDTO);
        return saveEntity(rating);
    }

    private RatingDTO saveEntity(Rating rating) {
        final var savedRating = ratingRepository.save(rating);
        return ratingMapper.toDTO(savedRating);
    }

    public void delete(Long id) {
        log.debug("Request to delete RATING with ID: {}", id);
        final var rating = ratingRepository.findById(id);
        if (rating.isEmpty()) throw new HttpNotFoundException("Entity not found");
        ratingRepository.deleteById(id);
    }
}
