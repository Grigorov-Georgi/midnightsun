package com.midnightsun.productservice.service.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String name;
}
