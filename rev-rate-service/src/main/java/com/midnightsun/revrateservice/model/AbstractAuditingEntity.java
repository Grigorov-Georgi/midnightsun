package com.midnightsun.revrateservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity {
        @CreatedBy
        @Column( nullable = false, updatable = false)
        private String createdBy;

        @CreatedDate
        @Column(updatable = false)
        private Instant createdDate = Instant.now();

        @LastModifiedBy
        @Column
        private String lastModifiedBy;

        @LastModifiedDate
        @Column
        private Instant lastModifiedDate = Instant.now();
}
