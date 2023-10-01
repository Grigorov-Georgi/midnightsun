package com.midnightsun.productservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO extends AbstractAuditingDTO {

    private Long id;

    private String text;

    private ProductDTO product;
}
