package com.midnightsun.productservice.web;

import com.midnightsun.productservice.service.CategoryService;
import com.midnightsun.productservice.service.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAll(Pageable pageable) {
        log.debug("REST request to get all CATEGORIES sorted by {}, page number: {} and page size: {}",
                pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());

        final var categories = categoryService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getOne(@PathVariable Long id) {
        log.debug("REST request to get CATEGORY by ID: {}", id);
        final var category = categoryService.getOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) {
        log.debug("REST request to save CATEGORY with content: {}", categoryDTO);
        final var savedCategory = categoryService.save(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO) {
        log.debug("REST request to updated CATEGORY with ID: {} with content {}", categoryDTO.getId(), categoryDTO);
        final var updatedCategory = categoryService.update(categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete CATEGORY with ID: {}", id);
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
