package com.midnightsun.productservice.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends AbstractAuditingDTO {

    private Long id;

    private String name;
}
