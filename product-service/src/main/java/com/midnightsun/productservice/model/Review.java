package com.midnightsun.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "review_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "review_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    private String text;

    @ManyToOne
    private Product product;
}