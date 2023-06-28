package com.midnightsun.orderservice.service;

import com.midnightsun.orderservice.mapper.CityMapper;
import com.midnightsun.orderservice.model.City;
import com.midnightsun.orderservice.repository.CityRepository;
import com.midnightsun.orderservice.service.dto.CityDTO;
import com.midnightsun.orderservice.web.exception.HttpBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CityService {
    private CityRepository cityRepository;
    private CityMapper cityMapper;

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public Page<CityDTO> getAll(Pageable pageable) {
        log.debug("Request to get all CITIES");
        return cityRepository.findAll(pageable).map(c -> cityMapper.toDTO(c));
    }

    public CityDTO getOne(Long id) {
        log.debug("Request to get CITY by ID: {}", id);
        return cityMapper.toDTO(cityRepository.findById(id).orElse(null));
    }

    public CityDTO save(CityDTO cityDTO) {
        log.debug("Request to save CITY: {}", cityDTO);
        if (cityDTO.getId() != null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        }
        final var city = cityMapper.toEntity(cityDTO);
        return save(city);
    }

    public CityDTO update(CityDTO cityDTO) {
        log.debug("Request to update CITY: {}", cityDTO);
        if (cityDTO.getId() == null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        }
        final var city = cityMapper.toEntity(cityDTO);
        return save(city);
    }

    private CityDTO save(City city) {
        final var savedCategory = cityRepository.save(city);
        return cityMapper.toDTO(savedCategory);
    }

    public void delete(Long id) {
        log.debug("Request to delete CITY with ID: {}", id);
        cityRepository.deleteById(id);
    }
}
