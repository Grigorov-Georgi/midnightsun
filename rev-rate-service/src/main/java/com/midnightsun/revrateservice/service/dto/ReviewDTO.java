package com.midnightsun.revrateservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long productId;

    private String text;
}
