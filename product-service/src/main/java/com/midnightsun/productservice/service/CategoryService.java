package com.midnightsun.productservice.service;

import com.midnightsun.productservice.mapper.CategoryMapper;
import com.midnightsun.productservice.model.Category;
import com.midnightsun.productservice.repository.CategoryRepository;
import com.midnightsun.productservice.service.dto.CategoryDTO;
import com.midnightsun.productservice.web.exception.HttpBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    public Page<CategoryDTO> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(p -> categoryMapper.toDTO(p));
    }

    public CategoryDTO getOne(Long id) {
        return categoryMapper.toDTO(categoryRepository.findById(id).orElse(null));
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        if (categoryDTO.getId() != null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        }
        final var category = categoryMapper.toEntity(categoryDTO);
        return save(category);
    }

    public CategoryDTO update(CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        }
        final var category = categoryMapper.toEntity(categoryDTO);
        return save(category);
    }

    private CategoryDTO save(Category category) {
        final var savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
