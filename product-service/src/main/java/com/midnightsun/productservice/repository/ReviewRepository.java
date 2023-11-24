package com.midnightsun.productservice.repository;

import com.midnightsun.productservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

//    List<Review> findAllByProductId(UUID productId);
//
//    @Query(value = "SELECT DISTINCT r.productId FROM Review r")
//    List<UUID> findAllDistinctProductIds();
}
