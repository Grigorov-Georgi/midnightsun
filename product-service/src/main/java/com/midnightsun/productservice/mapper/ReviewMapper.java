package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Review;
import com.midnightsun.productservice.service.dto.ReviewDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Review toEntity(ReviewDTO reviewDTO);

    ReviewDTO toDTO(Review review);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    ReviewDTO toDtoId(Review review);
}
