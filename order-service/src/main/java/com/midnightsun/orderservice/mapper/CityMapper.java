package com.midnightsun.orderservice.mapper;

import com.midnightsun.orderservice.model.City;
import com.midnightsun.orderservice.service.dto.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    City toEntity(CityDTO cityDTO);


    CityDTO toDTO(City city);
}
