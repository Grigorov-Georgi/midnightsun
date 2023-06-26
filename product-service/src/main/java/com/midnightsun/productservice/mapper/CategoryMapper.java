package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Category;
import com.midnightsun.productservice.service.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDTO categoryDTO);
    CategoryDTO toDTO(Category category);
}
