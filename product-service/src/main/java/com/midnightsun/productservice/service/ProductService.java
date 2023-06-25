package com.midnightsun.productservice.service;

import com.midnightsun.productservice.mapper.ProductMapper;
import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.repository.ProductRepository;
import com.midnightsun.productservice.service.dto.ProductDTO;
import com.midnightsun.productservice.web.exception.HttpBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO save(ProductDTO productDTO) {
        if (productDTO.getId() != null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        }
        final var product = productMapper.toEntity(productDTO);
        return save(product);
    }

    public ProductDTO update(ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        }
        final var product = productMapper.toEntity(productDTO);
        return save(product);
    }

    private ProductDTO save(Product product) {
        final var savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductDTO> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(p -> productMapper.toDTO(p));
    }

    public ProductDTO getOne(Long id) {
        return productMapper.toDTO(productRepository.findById(id).orElse(null));
    }
}
