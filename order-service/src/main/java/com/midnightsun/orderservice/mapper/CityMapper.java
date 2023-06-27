package com.midnightsun.orderservice.mapper;

import com.midnightsun.orderservice.model.City;
import com.midnightsun.orderservice.service.dto.CityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    City toEntity(CityDTO cityDTO);
    CityDTO toDTO(City city);
}
