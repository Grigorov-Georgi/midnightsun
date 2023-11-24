package com.midnightsun.productservice.repository;

import com.midnightsun.productservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

//    @Query(value = "SELECT DISTINCT r.productId FROM Rating r")
//    List<Long> findAllDistinctProductIds();
//
//    @Query(value = "SELECT AVG(r.score) FROM Rating r WHERE r.productId  = :productId")
//    Double getAverageRatingByProductId(@Param("productId") UUID productId);
}
