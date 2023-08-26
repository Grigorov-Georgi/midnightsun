package com.midnightsun.productservice.service;

import com.midnightsun.productservice.mapper.ProductMapper;
import com.midnightsun.productservice.repository.CategoryRepository;
import com.midnightsun.productservice.repository.ProductRepository;
import com.midnightsun.productservice.service.cache.CacheBufferRepository;
import com.midnightsun.productservice.service.dto.ProductDTO;
import com.midnightsun.productservice.web.exception.HttpBadRequestException;
import com.midnightsun.productservice.web.exception.HttpNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final CacheBufferRepository cacheBufferRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(CacheBufferRepository cacheBufferRepository,
                          ProductRepository productRepository,
                          ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.cacheBufferRepository = cacheBufferRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductDTO> getAll(Pageable pageable) {
        log.debug("Request to get all PRODUCTS");
        return productRepository.findAll(pageable).map(productMapper::toDTO);
    }

    public ProductDTO getOne(Long id) {
        log.debug("Request to get PRODUCT by ID: {}", id);
        return productMapper.toDTO(productRepository.findById(id).orElse(null));
    }

    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save PRODUCT: {}", productDTO);

        if (productDTO.getId() != null) throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        if (!categoryRepository.existsById(productDTO.getCategory().getId())) throw new HttpNotFoundException("Category not found!");

        final var product = productMapper.toEntity(productDTO);
        final var savedProduct = cacheBufferRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO update(ProductDTO productDTO) {
        log.debug("Request to update PRODUCT: {}", productDTO);

        if (productDTO.getId() == null) throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        if (!categoryRepository.existsById(productDTO.getCategory().getId())) throw new HttpNotFoundException("Category not found!");
        if (!productRepository.existsById(productDTO.getId())) throw new HttpNotFoundException("Product not found");

        final var product = productMapper.toEntity(productDTO);
        final var savedProduct = cacheBufferRepository.update(product);

        return productMapper.toDTO(savedProduct);
    }

    public void delete(Long id) {
        log.debug("Request to delete PRODUCT with ID: {}", id);
        productRepository.deleteById(id);
    }

    public Map<Long, Long> checkProductsAvailability(Map<Long, Long> productsIdQuantityMap) {
        List<ProductDTO> productsToSave = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : productsIdQuantityMap.entrySet()) {
            final var product = getOne(entry.getKey());

            if (product == null || (product.getQuantity() - entry.getValue()) < 0) {
                entry.setValue(-1L);
                return null;
            } else {
                product.setQuantity(product.getQuantity() - entry.getValue());
                productsToSave.add(product);
            }
        }
        productRepository.saveAll(productsToSave.stream().map(productMapper::toEntity).collect(Collectors.toList()));

        return productsIdQuantityMap;
    }
}
