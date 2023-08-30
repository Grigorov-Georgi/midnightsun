package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.service.dto.ProductDTO;
import com.midnightsun.productservice.service.cache.PrecomputedCacheService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProductMapperDecorator implements ProductMapper {

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private PrecomputedCacheService precomputedCacheService;

    @Override
    public Product toEntity(ProductDTO productDTO) {
        return mapper.toEntity(productDTO);
    }

    @Override
    public ProductDTO toDTO(Product product) {
        ProductDTO dto = mapper.toDTO(product);
        dto.setRatingScore(precomputedCacheService.getProductRatingScore(product.getId()));
        dto.setReviews(precomputedCacheService.getProductReviews(product.getId()));
        return dto;
    }
}
