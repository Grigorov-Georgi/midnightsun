package com.midnightsun.revrateservice.repository;

import com.midnightsun.revrateservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(Long productId);

    @Query(value = "SELECT DISTINCT r.productId FROM Review r")
    List<Long> findAllDistinctProductIds();
}
