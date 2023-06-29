package com.midnightsun.orderservice.model;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "city_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "city_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    private String name;
}
