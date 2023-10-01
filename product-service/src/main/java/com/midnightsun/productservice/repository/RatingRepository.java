package com.midnightsun.productservice.repository;

import com.midnightsun.productservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = "SELECT DISTINCT r.productId FROM Rating r")
    List<UUID> findAllDistinctProductIds();

    @Query(value = "SELECT AVG(r.score) FROM Rating r WHERE r.productId  = :productId")
    Double getAverageRatingByProductId(@Param("productId") UUID productId);
}
