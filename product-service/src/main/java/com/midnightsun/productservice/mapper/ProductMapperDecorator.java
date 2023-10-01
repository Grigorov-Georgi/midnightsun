package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.repository.RatingRepository;
import com.midnightsun.productservice.repository.ReviewRepository;
import com.midnightsun.productservice.service.dto.ProductDTO;
import com.midnightsun.productservice.service.deprecated.PrecomputedCacheService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProductMapperDecorator implements ProductMapper {

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Product toEntity(ProductDTO productDTO) {
        return mapper.toEntity(productDTO);
    }

    @Override
    public ProductDTO toDTO(Product product) {
        ProductDTO dto = mapper.toDTO(product);
        //Deprecated since 24.09.2023
//        dto.setRatingScore(precomputedCacheService.getProductRatingScore(product.getId()));
//        dto.setReviews(precomputedCacheService.getProductReviews(product.getId()));
        dto.setRatingScore(ratingRepository.getAverageRatingByProductId(product.getId()));
        return dto;
    }
}
