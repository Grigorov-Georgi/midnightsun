package com.midnightsun.productservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.io.Serializable;
import java.time.Instant;

@Data
public abstract class AbstractAuditingEntity implements Serializable{
        private static final long serialVersionUID = 1L;

        @ReadOnlyProperty
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String createdBy;

        @ReadOnlyProperty
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private Instant createdDate = Instant.now();

        @ReadOnlyProperty
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String lastModifiedBy;

        @ReadOnlyProperty
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private Instant lastModifiedDate = Instant.now();
}
