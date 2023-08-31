package com.midnightsun.revrateservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "review_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "review_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Type(type = "uuid-char")
    private UUID productId;

    private String text;
}