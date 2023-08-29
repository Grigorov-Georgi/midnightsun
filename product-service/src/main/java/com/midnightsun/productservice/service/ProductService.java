package com.midnightsun.productservice.service;

import com.midnightsun.productservice.mapper.ProductMapper;
import com.midnightsun.productservice.repository.CategoryRepository;
import com.midnightsun.productservice.repository.ProductRepository;
import com.midnightsun.productservice.service.cache.ProductCacheService;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCacheService productCacheService;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          ProductCacheService productCacheService,
                          ProductMapper productMapper,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productCacheService = productCacheService;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    //TODO: Implement solution for getAll using Cache
    public Page<ProductDTO> getAll(Pageable pageable) {
        log.debug("Request to get all PRODUCTS");
        return productRepository.findAll(pageable).map(productMapper::toDTO);
    }

    public ProductDTO getOne(UUID id) {
        log.debug("Request to get PRODUCT by ID: {}", id);
        return productMapper.toDTO(productCacheService.findById(id));
    }

    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save PRODUCT: {}", productDTO);

        if (productDTO.getId() != null) throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        if (!categoryRepository.existsById(productDTO.getCategory().getId())) throw new HttpNotFoundException("Category not found!");

        final var product = productMapper.toEntity(productDTO);
        final var savedProduct = productCacheService.save(product);

        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO update(ProductDTO productDTO) {
        log.debug("Request to update PRODUCT: {}", productDTO);

        if (productDTO.getId() == null) throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        if (!categoryRepository.existsById(productDTO.getCategory().getId())) throw new HttpNotFoundException("Category not found!");
        if (!productRepository.existsById(productDTO.getId())) throw new HttpNotFoundException("Product not found");

        final var product = productMapper.toEntity(productDTO);
        final var savedProduct = productCacheService.save(product);

        return productMapper.toDTO(savedProduct);
    }

    public void delete(UUID id) {
        log.debug("Request to delete PRODUCT with ID: {}", id);
        productCacheService.deleteById(id);
    }

    public Map<UUID, Long> checkProductsAvailability(Map<UUID, Long> productsIdQuantityMap) {
        List<ProductDTO> productsToSave = new ArrayList<>();

        for (Map.Entry<UUID, Long> entry : productsIdQuantityMap.entrySet()) {
            final var product = getOne(entry.getKey());

            if (product == null || (product.getQuantity() - entry.getValue()) < 0) {
                //TODO: Maybe next line of code is useless
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
