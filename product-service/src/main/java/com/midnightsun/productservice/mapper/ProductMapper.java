package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.service.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {
    Product toEntity(ProductDTO productDTO);
    ProductDTO toDTO(Product product);
}
