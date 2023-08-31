package com.midnightsun.revrateservice.service.dto;

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
public class RatingDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private UUID productId;

    private Double score;
}
