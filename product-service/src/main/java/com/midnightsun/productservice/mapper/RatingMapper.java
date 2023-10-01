package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Rating;
import com.midnightsun.productservice.service.dto.RatingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Rating toEntity(RatingDTO ratingDTO);

    RatingDTO toDTO(Rating rating);
}
