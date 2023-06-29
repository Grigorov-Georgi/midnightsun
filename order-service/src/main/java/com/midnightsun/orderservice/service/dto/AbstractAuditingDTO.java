package com.midnightsun.orderservice.service.dto;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public abstract class AbstractAuditingDTO implements Serializable {

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
}
