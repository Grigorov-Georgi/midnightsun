package com.midnightsun.revrateservice.mapper;

import com.midnightsun.revrateservice.model.Rating;
import com.midnightsun.revrateservice.service.dto.RatingDTO;
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
