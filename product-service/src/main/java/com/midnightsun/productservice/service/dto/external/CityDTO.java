package com.midnightsun.productservice.service.dto.external;

import com.midnightsun.productservice.service.dto.AbstractAuditingDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO extends AbstractAuditingDTO {

    private Long id;

    private String name;
}
