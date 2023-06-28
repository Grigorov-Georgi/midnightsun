package com.midnightsun.orderservice.model;

import javax.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity {
        @CreatedBy
        @Column( nullable = false, updatable = false)
        private String createdBy = "system";

        @CreatedDate
        @Column(updatable = false)
        private Instant createdDate = Instant.now();

        @LastModifiedBy
        @Column
        private String lastModifiedBy  = "system";

        @LastModifiedDate
        @Column
        private Instant lastModifiedDate = Instant.now();
}
