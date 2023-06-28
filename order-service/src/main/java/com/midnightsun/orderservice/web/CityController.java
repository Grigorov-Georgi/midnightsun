package com.midnightsun.orderservice.web;

import com.midnightsun.orderservice.service.CityService;
import com.midnightsun.orderservice.service.dto.CityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController( CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<Page<CityDTO>> getAll(Pageable pageable) {
        log.debug("REST request to get all CITIES sorted by {}, page number: {} and page size: {}",
                pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());

        final var cities = cityService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getOne(@PathVariable Long id) {
        log.debug("REST request to get CITY by ID: {}", id);
        final var city = cityService.getOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(city);
    }

    @PostMapping
    public ResponseEntity<CityDTO> save(@RequestBody CityDTO cityDTO) {
        log.debug("REST request to save CITY with content: {}", cityDTO);
        final var savedCity = cityService.save(cityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCity);
    }

    @PutMapping
    public ResponseEntity<CityDTO> update(@RequestBody CityDTO cityDTO) {
        log.debug("REST request to updated CITY with ID: {} with content {}", cityDTO.getId(), cityDTO);
        final var updatedCity = cityService.update(cityDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete CITY with ID: {}", id);
        cityService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
