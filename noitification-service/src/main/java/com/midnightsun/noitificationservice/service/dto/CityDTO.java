package com.midnightsun.noitificationservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO extends AbstractAuditingDTO{

    private Long id;

    private String name;
}
