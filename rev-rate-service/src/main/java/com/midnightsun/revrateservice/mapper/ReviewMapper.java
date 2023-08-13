package com.midnightsun.revrateservice.mapper;

import com.midnightsun.revrateservice.model.Review;
import com.midnightsun.revrateservice.service.dto.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Review toEntity(ReviewDTO reviewDTO);

    ReviewDTO toDTO(Review review);
}
