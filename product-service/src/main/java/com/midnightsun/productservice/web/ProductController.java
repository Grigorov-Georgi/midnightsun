package com.midnightsun.productservice.web;

import com.midnightsun.productservice.service.deprecated.ProductService;
import com.midnightsun.productservice.service.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAll(Pageable pageable) {
        log.debug("REST request to get all PRODUCTS sorted by {}, page number: {} and page size: {}",
                pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());

        final var products = productService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/top")
    public ResponseEntity<List<ProductDTO>> getTopProducts(@RequestParam(defaultValue = "5") Integer n) {
        log.debug("REST request to get top {} PRODUCTS", n);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getTopProducts(n));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable UUID id) {
        log.debug("REST request to get PRODUCT by ID: {}", id);
        final var product = productService.getOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        log.debug("REST request to save PRODUCT with content: {}", productDTO);
        final var savedProduct = productService.save(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) {
        log.debug("REST request to updated PRODUCT with ID: {} with content {}", productDTO.getId(), productDTO);
        final var updatedProduct = productService.update(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.debug("REST request to delete PRODUCT with ID: {}", id);
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
