package com.midnightsun.revrateservice.repository;

import com.midnightsun.revrateservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = "SELECT DISTINCT r.productId FROM Rating r")
    List<Long> findAllDistinctProductIds();

    @Query(value = "SELECT AVG(r.score) FROM Rating r WHERE r.productId  = :productId")
    Double getAverageRatingByProductId(@Param("productId") Long productId);
}