package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Category;
import com.midnightsun.productservice.service.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);
}
