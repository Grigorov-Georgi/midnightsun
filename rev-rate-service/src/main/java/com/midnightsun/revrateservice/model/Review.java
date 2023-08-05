package com.midnightsun.revrateservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

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

    private Long productId;

    private String text;
}