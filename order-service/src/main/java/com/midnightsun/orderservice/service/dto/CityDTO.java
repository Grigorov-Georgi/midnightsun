package com.midnightsun.orderservice.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO extends AbstractAuditingDTO {

    private Long id;

    private String name;
}
